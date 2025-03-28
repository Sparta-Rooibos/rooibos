package com.sparta.rooibos.auth.infrastructure.redis;

import com.sparta.rooibos.auth.application.dto.UserAuthDTO;
import com.sparta.rooibos.auth.application.service.port.RedisProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisProviderImpl implements RedisProvider {
    private final AuthCacheService authCacheService;

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
}
