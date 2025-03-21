package com.spring.cloud.client.auth.application.service.adapter;

import com.spring.cloud.client.auth.application.dto.request.LoginRequest;
import com.spring.cloud.client.auth.application.dto.UserDTO;
import com.spring.cloud.client.auth.application.exception.BusinessAuthException;
import com.spring.cloud.client.auth.application.exception.custom.AuthErrorCode;
import com.spring.cloud.client.auth.application.service.port.AuthService;
import com.spring.cloud.client.auth.application.service.port.CookieProvider;
import com.spring.cloud.client.auth.application.service.port.JwtProvider;
import com.spring.cloud.client.auth.application.service.port.RedisProvider;
import com.spring.cloud.client.auth.domain.entity.Refresh;
import com.spring.cloud.client.auth.domain.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final RefreshRepository refreshRepository;
    private final RedisProvider redisProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CookieProvider cookieProvider;

    @Override
    @Transactional
    public void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        if (redisProvider.isTokenBlacklisted(loginRequest.email())) {
            throw new BusinessAuthException(AuthErrorCode.BLOCKED_ACCOUNT);
        }

        Optional<UserDTO> cachedUser = redisProvider.getUserInfo(loginRequest.email());
        if (cachedUser.isEmpty()) {
            throw new BusinessAuthException(AuthErrorCode.INVALID_CREDENTIALS);
        }
        UserDTO user = cachedUser.get();

        if (!passwordEncoder.matches(loginRequest.password(), user.password())) {
            throw new BusinessAuthException(AuthErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.createJwt("access", user.email(), user.role(), 600000L);
        String refreshToken = jwtProvider.createJwt("refresh", user.email(), user.role(), 86400000L);
        refreshRepository.save(Refresh.create(user.email(), refreshToken, 86400000L));

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(cookieProvider.createCookie(refreshToken));
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieProvider.getRefreshToken(request);

        // 리프레시토큰 만료여부를 따지지 않는 것은 무조건 로그아웃을 눌러서 로그아웃하므로 만료를 떠나 로그아웃 시켜버리는 로직이다
        // 만약 만료된 것 땜에 로그아웃이 된다면 그것은 로그인 시도했을 때 즉 게이트웨이에서부터 거절되어야 하는게 맞다
        if (refreshToken == null) {
            throw new BusinessAuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        refreshRepository.deleteByRefresh(refreshToken);

        cookieProvider.deleteRefreshToken(response);
        response.setHeader("Authorization", "");
    }

    @Override
    @Transactional
    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieProvider.getRefreshToken(request);
        if (refreshToken == null || jwtProvider.isExpired(refreshToken)) {
            throw new BusinessAuthException(AuthErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        String category = jwtProvider.getCategory(refreshToken);
        if (!"refresh".equals(category)) {
            throw new BusinessAuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        String email = jwtProvider.getEmail(refreshToken);
        String role = jwtProvider.getRole(refreshToken);
        String newAccessToken = jwtProvider.createJwt("access", email, role, 600000L);
        String newRefreshToken = jwtProvider.createJwt("refresh", email, role, 86400000L);

        refreshRepository.deleteByRefresh(refreshToken); // 기존 Refresh 토큰 제거
        refreshRepository.save(Refresh.create(email, newRefreshToken, 86400000L)); // 새로 저장

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(cookieProvider.createCookie(newRefreshToken));
    }

    @Override
    @Transactional
    public void banUser(String email) {
        refreshRepository.deleteByEmail(email);
    }
}
