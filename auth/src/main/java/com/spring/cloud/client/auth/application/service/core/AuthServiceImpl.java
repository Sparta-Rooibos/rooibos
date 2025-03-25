package com.spring.cloud.client.auth.application.service.core;

import com.spring.cloud.client.auth.application.dto.request.LoginRequest;
import com.spring.cloud.client.auth.application.dto.UserAuthDTO;
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
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final RefreshRepository refreshRepository;
    private final RedisProvider redisProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CookieProvider cookieProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        String key = "blacklist:" + loginRequest.email();
        String blockedAtStr = redisTemplate.opsForValue().get(key);

        if (blockedAtStr != null) {
            Instant blockedAt = Instant.ofEpochSecond(Long.parseLong(blockedAtStr));
            if (Instant.now().isBefore(blockedAt)) {
                throw new BusinessAuthException(AuthErrorCode.BLOCKED_ACCOUNT);
            }
        }
        Optional<UserAuthDTO> cachedUser = redisProvider.getUserInfo(loginRequest.email());
        if (cachedUser.isEmpty()) {
            throw new BusinessAuthException(AuthErrorCode.INVALID_CREDENTIALS);
        }
        UserAuthDTO user = cachedUser.get();

        if (!passwordEncoder.matches(loginRequest.password(), user.password())) {
            throw new BusinessAuthException(AuthErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.createJwt("access", user.username(), user.email(), user.role(), 600000L);
        String refreshToken = jwtProvider.createJwt("refresh", user.username(), user.email(), user.role(), 86400000L);
        refreshRepository.save(Refresh.create(user.email(), refreshToken, 86400000L));

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(cookieProvider.createCookie(refreshToken));
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieProvider.getRefreshToken(request);

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

        String username = jwtProvider.getUsername(refreshToken);
        String email = jwtProvider.getEmail(refreshToken);
        String role = jwtProvider.getRole(refreshToken);
        String newAccessToken = jwtProvider.createJwt("access", username, email, role, 600000L);
        String newRefreshToken = jwtProvider.createJwt("refresh", username, email, role, 86400000L);

        refreshRepository.deleteByRefresh(refreshToken);
        refreshRepository.save(Refresh.create(email, newRefreshToken, 86400000L));

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(cookieProvider.createCookie(newRefreshToken));
    }

    @Override
    @Transactional
    public void banUser(String email) {
        refreshRepository.deleteByEmail(email);
    }
}
