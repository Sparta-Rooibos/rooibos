package com.spring.cloud.client.auth.infrastructure.init;

import com.spring.cloud.client.auth.application.dto.UserAuthDTO;
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
        };
    }
}
