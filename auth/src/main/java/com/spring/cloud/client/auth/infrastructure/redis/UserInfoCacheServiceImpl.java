package com.spring.cloud.client.auth.infrastructure.redis;

import com.spring.cloud.client.auth.application.dto.CachedUserResponse;
import com.spring.cloud.client.auth.application.service.port.UserInfoCacheService;
import com.spring.cloud.client.auth.infrastructure.feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoCacheServiceImpl implements UserInfoCacheService {

    private final UserClient userClient;

    @Override
    @Cacheable(cacheNames = "user_info", key = "#email")
    public CachedUserResponse getUserForAuth(String email) {
        return userClient.getUserForAuth(email);
    }
}
