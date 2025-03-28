package com.sparta.rooibos.auth.infrastructure.redis;

import com.sparta.rooibos.auth.application.dto.UserAuthDTO;
import com.sparta.rooibos.auth.application.dto.UserStreamEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, UserStreamEvent> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, UserStreamEvent> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<UserStreamEvent> serializer = new Jackson2JsonRedisSerializer<>(UserStreamEvent.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        return template;
    }

    @Bean
    public RedisTemplate<String, UserAuthDTO> userAuthRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, UserAuthDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<UserAuthDTO> serializer = new Jackson2JsonRedisSerializer<>(UserAuthDTO.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

}
