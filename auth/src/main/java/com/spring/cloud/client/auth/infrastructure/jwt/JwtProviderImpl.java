package com.spring.cloud.client.auth.infrastructure.jwt;

import com.spring.cloud.client.auth.application.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtProviderImpl implements JwtProvider {
    private final JwtGenerator jwtGenerator;
    private final JwtValidator jwtValidator;

    @Override
    public String createJwt(String type, String username, String role, long expiration) {
        return jwtGenerator.createJwt(type, username, role, expiration);
    }

    @Override
    public boolean isExpired(String token) {
        return jwtValidator.isExpired(token);
    }

    @Override
    public String getCategory(String token) {
        return jwtValidator.getCategory(token);
    }

    @Override
    public String getUsername(String token) {
        return jwtValidator.getUsername(token);
    }

    @Override
    public String getRole(String token) {
        return jwtValidator.getRole(token);
    }
}

