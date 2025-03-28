package com.spring.cloud.client.auth.infrastructure.redis;

import com.spring.cloud.client.auth.application.dto.AuthStreamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthCacheService {
    private final @Qualifier("authCacheRedisTemplate") RedisTemplate<String, AuthStreamResponse> redisTemplate;

    private static final String CACHE_PREFIX = "user:";

    public void createUserInfo(AuthStreamResponse response) {
        redisTemplate.opsForValue().set(CACHE_PREFIX + response.email(), response);
    }

    public Optional<AuthStreamResponse> getUserInfo(String email) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(CACHE_PREFIX + email));
    }

    public void deleteUserInfo(String email) {
        redisTemplate.delete(CACHE_PREFIX + email);
    }
}