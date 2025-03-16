package com.spring.cloud.client.auth.infrastructure.redis;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BlacklistService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String BLACKLIST_PREFIX = "auth:blacklist:";
    private static final long BLACKLIST_TTL = 60 * 24; // 블랙리스트 유지시간 (24시간)

    public void addToBlacklist(String token) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "BLOCKED", BLACKLIST_TTL, TimeUnit.MINUTES);
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }

//    public void blacklistRefreshToken(String email) {
//        Set<String> refreshTokens = redisTemplate.keys(REFRESH_PREFIX + email + ":*");
//
//        if (refreshTokens != null) {
//            for (String tokenKey : refreshTokens) {
//                String token = redisTemplate.opsForValue().get(tokenKey);
//                if (token != null) {
//                    redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "BLOCKED", BLACKLIST_TTL, TimeUnit.MINUTES);
//                }
//                redisTemplate.delete(tokenKey);
//            }
//        }
//    }
//
//    public void blacklistAccessToken(String email) {
//        Set<String> accessTokens = redisTemplate.keys(ACCESS_PREFIX + email + ":*");
//
//        if (accessTokens != null) {
//            for (String tokenKey : accessTokens) {
//                String token = redisTemplate.opsForValue().get(tokenKey);
//                if (token != null) {
//                    redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "BLOCKED", BLACKLIST_TTL, TimeUnit.MINUTES);
//                }
//                redisTemplate.delete(tokenKey);
//            }
//        }
//    }

}