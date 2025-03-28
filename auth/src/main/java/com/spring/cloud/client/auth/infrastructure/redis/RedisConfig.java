package com.spring.cloud.client.auth.infrastructure.redis;

import com.spring.cloud.client.auth.application.dto.AuthStreamResponse;
import com.spring.cloud.client.auth.application.dto.AuthStreamRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, AuthStreamResponse> authCacheRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, AuthStreamResponse> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<AuthStreamResponse> serializer = new Jackson2JsonRedisSerializer<>(AuthStreamResponse.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        return template;
    }

    @Bean(name = "authStreamRedisTemplate")
    public RedisTemplate<String, AuthStreamRequest> authStreamRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, AuthStreamRequest> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<AuthStreamRequest> serializer = new Jackson2JsonRedisSerializer<>(AuthStreamRequest.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        return template;
    }

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, AuthStreamRequest>> streamListenerContainer(
            RedisConnectionFactory connectionFactory
    ) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, AuthStreamRequest>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofMillis(500)) // 필요시 조정 가능
                        .targetType(AuthStreamRequest.class)
                        .build();

        return StreamMessageListenerContainer.create(connectionFactory, options);
    }

}
