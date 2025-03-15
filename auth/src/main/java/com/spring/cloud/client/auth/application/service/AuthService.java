package com.spring.cloud.client.auth.application.service;

import com.spring.cloud.client.auth.application.dto.request.LoginRequest;
import com.spring.cloud.client.auth.application.dto.UserDTO;
import com.spring.cloud.client.auth.domain.entity.Refresh;
import com.spring.cloud.client.auth.domain.repository.RefreshRepository;
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
    private final JwtProvider jwtProvider;
    private final RefreshRepository refreshRepository;
    private final RedisProvider redisProvider;
    private final EventService eventService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CookieProvider cookieProvider;

    @Transactional
    public ResponseEntity<?> login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        // ✅ 1) Redis에서 유저 정보 캐싱 확인
        Optional<UserDTO> cachedUser = redisProvider.getUserInfo(loginRequest.username());

        UserDTO user = cachedUser.orElseGet(() -> {
            // ✅ 2) Kafka로 유저 인증 요청 (비동기 메시징)
            UserDTO fetchedUser = eventService.requestUserInfo(loginRequest.username());
            redisProvider.createUserInfo(fetchedUser); // 캐싱 저장
            return fetchedUser;
        });

        if (!passwordEncoder.matches(loginRequest.password(), user.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        String existingRefreshToken = cookieProvider.getRefreshToken(request);

        if (redisProvider.isTokenBlacklisted(existingRefreshToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 계정은 보안 조치로 로그인할 수 없습니다.");
        }

        String accessToken = jwtProvider.createJwt("access", user.username(), user.role(), 600000L);
        String refreshToken = jwtProvider.createJwt("refresh", user.username(), user.role(), 86400000L);
        refreshRepository.save(Refresh.create(user.username(), refreshToken, 86400000L));

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(cookieProvider.createCookie(refreshToken));
        return ResponseEntity.ok("로그인 성공");
    }

    @Transactional
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieProvider.getRefreshToken(request);

        // 리프레시토큰 만료여부를 따지지 않는 것은 무조건 로그아웃을 눌러서 로그아웃하므로 만료를 떠나 로그아웃 시켜버리는 로직이다
        // 만약 만료된 것 땜에 로그아웃이 된다면 그것은 로그인 시도했을 때 즉 게이트웨이에서부터 거절되어야 하는게 맞다
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("유효하지 않은 Refresh Token입니다.");
        }

        refreshRepository.deleteByRefresh(refreshToken);

        cookieProvider.deleteRefreshToken(response);
        response.setHeader("Authorization", "");
        return ResponseEntity.ok("로그아웃 성공");
    }

    @Transactional
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieProvider.getRefreshToken(request);
        if (refreshToken == null || jwtProvider.isExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인이 필요합니다.");
        }

        String category = jwtProvider.getCategory(refreshToken);
        if (!"refresh".equals(category)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 Refresh Token입니다.");
        }

        String username = jwtProvider.getUsername(refreshToken);
        String role = jwtProvider.getRole(refreshToken);
        String newAccessToken = jwtProvider.createJwt("access", username, role, 600000L);
        String newRefreshToken = jwtProvider.createJwt("refresh", username, role, 86400000L);

        refreshRepository.deleteByRefresh(refreshToken); // 기존 Refresh 토큰 제거
        refreshRepository.save(Refresh.create(username, newRefreshToken, 86400000L)); // 새로 저장

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(cookieProvider.createCookie(newRefreshToken));

        return ResponseEntity.ok("리프레시 토큰 재발급 성공");
    }
}
