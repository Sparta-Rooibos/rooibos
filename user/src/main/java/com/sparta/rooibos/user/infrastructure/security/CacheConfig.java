package com.sparta.rooibos.user.infrastructure.security;

import com.sparta.rooibos.user.application.auditing.UserAuditorContext;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CacheConfig {

    @Bean("auditorKeyGenerator")
    public KeyGenerator auditorKeyGenerator() {
        return (target, method, params) -> UserAuditorContext.getEmail();
    }
}