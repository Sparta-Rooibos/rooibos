package com.spring.cloud.client.auth.infrastructure.redis;

import com.spring.cloud.client.auth.application.dto.UserAuthDTO;
import com.spring.cloud.client.auth.application.service.port.RedisProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisProviderImpl implements RedisProvider {
    private final AuthCacheService authCacheService;
    private final BlacklistService blacklistService;

    @Override
    public void createUserInfo(UserAuthDTO user) {
        authCacheService.createUserInfo(user);
    }

    @Override
    public Optional<UserAuthDTO> getUserInfo(String username) {
        return authCacheService.getUserInfo(username);
    }

    @Override
    public void deleteUserInfo(String username) {
        authCacheService.deleteUserInfo(username);
    }

    @Override
    public void addToBlacklist(String email) {
        blacklistService.addToBlacklist(email);
    }

    @Override
    public boolean isTokenBlacklisted(String email) {
        return blacklistService.isTokenBlacklisted(email);
    }
}
