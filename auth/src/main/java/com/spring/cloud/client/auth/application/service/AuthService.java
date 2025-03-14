package com.spring.cloud.client.auth.application.service;

import com.spring.cloud.client.auth.application.dto.LoginDTO;
import com.spring.cloud.client.auth.application.dto.UserDTO;
import com.spring.cloud.client.auth.domain.entity.Refresh;
import com.spring.cloud.client.auth.domain.repository.RefreshRepository;
import com.spring.cloud.client.auth.infrastructure.jwt.JwtGenerator;
import com.spring.cloud.client.auth.infrastructure.jwt.JwtValidator;
import com.spring.cloud.client.auth.infrastructure.kafka.KafkaProducer;
import com.spring.cloud.client.auth.infrastructure.redis.AuthCacheService;
import com.spring.cloud.client.auth.infrastructure.redis.BlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtGenerator jwtGenerator;
    private final JwtValidator jwtValidator;
    private final RefreshRepository refreshRepository;
    private final AuthCacheService authCacheService;
    private final KafkaProducer kafkaProducer;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CookieService cookieService;
    private final BlacklistService blacklistService;

    @Transactional
    public ResponseEntity<?> login(LoginDTO loginRequest, HttpServletRequest request, HttpServletResponse response) {
        // ✅ 1) Redis에서 유저 정보 캐싱 확인
        Optional<UserDTO> cachedUser = authCacheService.getUserInfo(loginRequest.getUsername());

        UserDTO user = cachedUser.orElseGet(() -> {
            // ✅ 2) Kafka로 유저 인증 요청 (비동기 메시징)
            UserDTO fetchedUser = kafkaProducer.requestUserInfo(loginRequest.getUsername());
            authCacheService.createUserInfo(fetchedUser); // 캐싱 저장
            return fetchedUser;
        });

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        String existingRefreshToken = cookieService.getRefreshToken(request);

        if (blacklistService.isTokenBlacklisted(existingRefreshToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 계정은 보안 조치로 로그인할 수 없습니다.");
        }

        String accessToken = jwtGenerator.createJwt("access", user.getUsername(), user.getRole(), 600000L);
        String refreshToken = jwtGenerator.createJwt("refresh", user.getUsername(), user.getRole(), 86400000L);
        refreshRepository.save(Refresh.create(user.getUsername(), refreshToken, 86400000L));

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(cookieService.createCookie("refresh", refreshToken));
        return ResponseEntity.ok("로그인 성공");
    }

    @Transactional
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieService.getRefreshToken(request);

        // 리프레시토큰 만료여부를 따지지 않는 것은 무조건 로그아웃을 눌러서 로그아웃하므로 만료를 떠나 로그아웃 시켜버리는 로직이다
        // 만약 만료된 것 땜에 로그아웃이 된다면 그것은 로그인 시도했을 때 즉 게이트웨이에서부터 거절되어야 하는게 맞다
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("유효하지 않은 Refresh Token입니다.");
        }

        refreshRepository.deleteByRefresh(refreshToken);

        cookieService.deleteRefreshToken(response);
        response.setHeader("Authorization", "");
        return ResponseEntity.ok("로그아웃 성공");
    }

    @Transactional
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieService.getRefreshToken(request);
        if (refreshToken == null || jwtValidator.isExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인이 필요합니다.");
        }

        String category = jwtValidator.getCategory(refreshToken);
        if (!"refresh".equals(category)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 Refresh Token입니다.");
        }

        String username = jwtValidator.getUsername(refreshToken);
        String role = jwtValidator.getRole(refreshToken);
        String newAccessToken = jwtGenerator.createJwt("access", username, role, 600000L);
        String newRefreshToken = jwtGenerator.createJwt("refresh", username, role, 86400000L);

        refreshRepository.deleteByRefresh(refreshToken); // 기존 Refresh 토큰 제거
        refreshRepository.save(Refresh.create(username, newRefreshToken, 86400000L)); // 새로 저장

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(cookieService.createCookie("refresh", newRefreshToken));

        return ResponseEntity.ok("리프레시 토큰 재발급 성공");
    }
}
