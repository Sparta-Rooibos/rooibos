package com.sparta.rooibos.user.infrastructure.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.rooibos.user.application.dto.UserStreamResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
//    @Bean
//    public RedisTemplate<String, UserStreamResponse> userStreamRedisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, UserStreamResponse> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        Jackson2JsonRedisSerializer<UserStreamResponse> serializer = new Jackson2JsonRedisSerializer<>(UserStreamResponse.class);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(serializer);
//        return template;
//    }

//    import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

    @Bean
    public RedisTemplate<String, UserStreamResponse> userStreamRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserStreamResponse> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

//    @Bean
//    public RedisTemplate<String, UserStreamResponse> userStreamRedisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, UserStreamResponse> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.activateDefaultTyping(
//                LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL,
//                JsonTypeInfo.As.PROPERTY
//        );
//
//        Jackson2JsonRedisSerializer<UserStreamResponse> serializer =
//                new Jackson2JsonRedisSerializer<>(objectMapper, UserStreamResponse.class);
//
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(serializer);
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(serializer);
//
//        return template;
//    }
}