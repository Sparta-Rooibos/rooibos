package com.sparta.rooibos.user.infrastructure.redis;

import com.sparta.rooibos.user.application.service.port.BlacklistProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BlacklistService implements BlacklistProvider {

    @Qualifier("blacklistRedisTemplate")
    private final StringRedisTemplate redisTemplate;

    public void addToBlacklist(String email, long ttlSeconds) {
        String key = "blacklist:" + email;
        String nowTimestamp = String.valueOf(Instant.now().getEpochSecond());

        redisTemplate.opsForValue().set(key, nowTimestamp, ttlSeconds, TimeUnit.SECONDS);
    }
}