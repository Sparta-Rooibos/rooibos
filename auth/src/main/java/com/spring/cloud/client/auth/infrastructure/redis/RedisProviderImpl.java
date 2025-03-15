package com.spring.cloud.client.auth.infrastructure.redis;

import com.spring.cloud.client.auth.application.dto.UserDTO;
import com.spring.cloud.client.auth.application.service.RedisProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisProviderImpl implements RedisProvider {
    private final AuthCacheService authCacheService;
    private final BlacklistService blacklistService;

    @Override
    public void createUserInfo(UserDTO user) {
        authCacheService.createUserInfo(user);
    }

    @Override
    public Optional<UserDTO> getUserInfo(String username) {
        return authCacheService.getUserInfo(username);
    }

    @Override
    public void deleteUserCache(String username) {
        authCacheService.deleteUserCache(username);
    }

    @Override
    public void addToBlacklist(String token) {
        blacklistService.addToBlacklist(token);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistService.isTokenBlacklisted(token);
    }
}
