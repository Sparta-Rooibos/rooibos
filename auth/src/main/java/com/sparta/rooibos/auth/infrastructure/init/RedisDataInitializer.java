package com.sparta.rooibos.auth.infrastructure.init;

import com.sparta.rooibos.auth.application.dto.UserAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisDataInitializer {

    private final RedisTemplate<String, UserAuthDTO> userCacheRedisTemplate;

    @Bean
    public CommandLineRunner initMasterUserCache() {
        return args -> {
            String username = "masteradmin";
            String email = "master01";
            String password = "$2a$10$TbdzfMn69mOrYdZL9uhEvu94IiOwaBhgASzCIvCDyKDDe4xlSJ/3K";
            String role = "ROLE_MASTER";

            UserAuthDTO dto = new UserAuthDTO(username, email, password, role);

            String redisKey = "user:" + email;
            userCacheRedisTemplate.opsForValue().set(redisKey, dto);

            System.out.println("[✅ Redis 초기화] 마스터 계정 캐싱 완료: " + redisKey);

            String hubusername = "hub";
            String hubemail = "hub01";
            String hubpassword = "$2a$10$TbdzfMn69mOrYdZL9uhEvu94IiOwaBhgASzCIvCDyKDDe4xlSJ/3K"; // 같은 암호화된 비번 사용
            String hubrole = "ROLE_HUB";


            UserAuthDTO hubdto = new UserAuthDTO(hubusername, hubemail, hubpassword, hubrole);
            String redisKey2 = "user:" + hubemail;
            userCacheRedisTemplate.opsForValue().set(redisKey2, hubdto);
            System.out.println("[✅ Redis 초기화] 허브 계정 캐싱 완료: user:" + redisKey2);

            String deliveryusername = "delivery";
            String deliveryemail = "delivery01";
            String deliverypassword = "$2a$10$TbdzfMn69mOrYdZL9uhEvu94IiOwaBhgASzCIvCDyKDDe4xlSJ/3K"; // 같은 암호화된 비번 사용
            String deliveryrole = "ROLE_DELIVERY";


            UserAuthDTO deliverydto = new UserAuthDTO(deliveryusername, deliveryemail, deliverypassword, deliveryrole);
            String redisKey3 = "user:" + deliveryemail;
            userCacheRedisTemplate.opsForValue().set(redisKey3, hubdto);
            System.out.println("[✅ Redis 초기화] 배달 계정 캐싱 완료: user:" + redisKey3);

            String clientusername = "client";
            String clientemail = "client01";
            String clientpassword = "$2a$10$TbdzfMn69mOrYdZL9uhEvu94IiOwaBhgASzCIvCDyKDDe4xlSJ/3K"; // 같은 암호화된 비번 사용
            String clientrole = "ROLE_HUB";


            UserAuthDTO clientdto = new UserAuthDTO(clientusername, clientemail, clientpassword, clientrole);
            String redisKey4 = "user:" + clientemail;
            userCacheRedisTemplate.opsForValue().set(redisKey4, clientdto);
            System.out.println("[✅ Redis 초기화] 업체 계정 캐싱 완료: user:" + redisKey4);
        };
    }
}
