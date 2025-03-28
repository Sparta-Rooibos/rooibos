package com.spring.cloud.client.auth.infrastructure.redis;

import com.spring.cloud.client.auth.application.dto.AuthStreamResponse;
import com.spring.cloud.client.auth.application.service.port.RedisProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisProviderImpl implements RedisProvider {
    private final AuthCacheService authCacheService;

    @Override
    public void createUserInfo(AuthStreamResponse response) {
        authCacheService.createUserInfo(response);
    }

    @Override
    public Optional<AuthStreamResponse> getUserInfo(String username) {
        return authCacheService.getUserInfo(username);
    }

    @Override
    public void deleteUserInfo(String username) {
        authCacheService.deleteUserInfo(username);
    }
}
