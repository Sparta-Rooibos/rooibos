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

    private static final String CACHE_PREFIX = "auth:user:";
    private static final long CACHE_TTL = 30;

    public void createUserInfo(UserDTO userDTO) {
        redisTemplate.opsForValue().set(CACHE_PREFIX + userDTO.email(), userDTO, CACHE_TTL, TimeUnit.MINUTES);
    }

    public Optional<UserDTO> getUserInfo(String email) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(CACHE_PREFIX + email));
    }

    public void deleteUserInfo(String email) {
        redisTemplate.delete(CACHE_PREFIX + email);
    }
}