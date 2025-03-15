package com.spring.cloud.client.auth.infrastructure.redis;


import com.spring.cloud.client.auth.application.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthCacheService {
    private final RedisTemplate<String, UserDTO> redisTemplate;
    private final BlacklistService blacklistService;

    private static final String CACHE_PREFIX = "auth:user:";
    private static final long CACHE_TTL = 30;

    // 사용자 정보 캐싱
    public void createUserInfo(UserDTO user) {
        redisTemplate.opsForValue().set(CACHE_PREFIX + user.getUsername(), user, CACHE_TTL, TimeUnit.MINUTES);
    }

    // 사용자 정보 조회
    public Optional<UserDTO> getUserInfo(String username) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(CACHE_PREFIX + username));
    }

    // 특정 유저 캐시 삭제
    public void deleteUserCache(String username) {
        redisTemplate.delete(CACHE_PREFIX + username);
    }
}