package com.sparta.rooibos.auth.infrastructure.redis;

import com.sparta.rooibos.auth.application.dto.UserAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthCacheService {
    private final RedisTemplate<String, UserAuthDTO> redisTemplate;

    private static final String CACHE_PREFIX = "user:";

    public void createUserInfo(UserAuthDTO userAuthDTO) {
        redisTemplate.opsForValue().set(CACHE_PREFIX + userAuthDTO.email(), userAuthDTO);
    }

    public Optional<UserAuthDTO> getUserInfo(String email) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(CACHE_PREFIX + email));
    }

    public void deleteUserInfo(String email) {
        redisTemplate.delete(CACHE_PREFIX + email);
    }
}