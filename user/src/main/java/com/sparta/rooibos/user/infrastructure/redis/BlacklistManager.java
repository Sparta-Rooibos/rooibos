package com.sparta.rooibos.user.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BlacklistManager {

    private final StringRedisTemplate redisTemplate;

    public void blacklistUser(String email, long ttlSeconds) {
        String key = "blacklist:" + email;
        String nowTimestamp = String.valueOf(Instant.now().getEpochSecond());

        redisTemplate.opsForValue().set(key, nowTimestamp, ttlSeconds, TimeUnit.SECONDS);
    }
}