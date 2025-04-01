package com.sparta.rooibos.user.infrastructure.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class BlacklistRedisConfig {
    @Value("${spring.redis.blacklist.host}")
    private String host;

    @Value("${spring.redis.blacklist.port}")
    private int port;

    @Bean(name = "blacklistRedisConnectionFactory")
    public LettuceConnectionFactory blacklistRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        return new LettuceConnectionFactory(config);
    }

    @Bean(name = "blacklistRedisTemplate")
    public StringRedisTemplate blacklistRedisTemplate() {
        return new StringRedisTemplate(blacklistRedisConnectionFactory());
    }
}