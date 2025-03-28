package com.spring.cloud.client.auth.infrastructure.init;

import com.spring.cloud.client.auth.application.dto.AuthStreamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisDataInitializer {

    private final RedisTemplate<String, AuthStreamResponse> userCacheRedisTemplate;

    @Bean
    public CommandLineRunner initMasterUserCache() {
        return args -> {
            String username = "masteradmin";
            String email = "master01";
            String password = "$2a$10$TbdzfMn69mOrYdZL9uhEvu94IiOwaBhgASzCIvCDyKDDe4xlSJ/3K";
            String role = "ROLE_MASTER";

            AuthStreamResponse dto = new AuthStreamResponse(username, email, password, role);

            String redisKey = "user:" + email;
            userCacheRedisTemplate.opsForValue().set(redisKey, dto);

            System.out.println("[Redis 초기화] 마스터 계정 캐싱 완료: " + redisKey);

            String hubusername = "hub";
            String hubemail = "hub01";
            String hubpassword = "$2a$10$TbdzfMn69mOrYdZL9uhEvu94IiOwaBhgASzCIvCDyKDDe4xlSJ/3K"; // 같은 암호화된 비번 사용
            String hubrole = "ROLE_HUB";


            AuthStreamResponse hubdto = new AuthStreamResponse(hubusername, hubemail, hubpassword, hubrole);
            String redisKey2 = "user:" + hubemail;
            userCacheRedisTemplate.opsForValue().set(redisKey2, hubdto);
            System.out.println("[Redis 초기화] 허브 계정 캐싱 완료: user:" + redisKey2);

            String deliveryusername = "delivery";
            String deliveryemail = "delivery01";
            String deliverypassword = "$2a$10$TbdzfMn69mOrYdZL9uhEvu94IiOwaBhgASzCIvCDyKDDe4xlSJ/3K"; // 같은 암호화된 비번 사용
            String deliveryrole = "ROLE_DELIVERY";


            AuthStreamResponse deliverydto = new AuthStreamResponse(deliveryusername, deliveryemail, deliverypassword, deliveryrole);
            String redisKey3 = "user:" + deliveryemail;
            userCacheRedisTemplate.opsForValue().set(redisKey3, hubdto);
            System.out.println("[Redis 초기화] 배달 계정 캐싱 완료: user:" + redisKey3);

            String clientusername = "client";
            String clientemail = "client01";
            String clientpassword = "$2a$10$TbdzfMn69mOrYdZL9uhEvu94IiOwaBhgASzCIvCDyKDDe4xlSJ/3K"; // 같은 암호화된 비번 사용
            String clientrole = "ROLE_HUB";


            AuthStreamResponse clientdto = new AuthStreamResponse(clientusername, clientemail, clientpassword, clientrole);
            String redisKey4 = "user:" + clientemail;
            userCacheRedisTemplate.opsForValue().set(redisKey4, clientdto);
            System.out.println("[Redis 초기화] 업체 계정 캐싱 완료: user:" + redisKey4);
        };
    }
}
