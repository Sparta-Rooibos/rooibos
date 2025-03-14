package com.spring.cloud.client.auth.infrastructure.kafka;

import com.spring.cloud.client.auth.application.dto.UserDTO;
import com.spring.cloud.client.auth.infrastructure.redis.AuthCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final AuthCacheService authCacheService;

    @KafkaListener(topics = "user-service-auth-response", groupId = "auth-service")
    public void listenUserInfoResponse(UserDTO userDTO) {
        try {
            log.info("🔄 Kafka 응답 수신 - username: {}", userDTO.getUsername());
            authCacheService.createUserInfo(userDTO);
        } catch (Exception e) {
            log.error("⚠️ Kafka 메시지 처리 중 오류 발생 - error: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "user-service-update", groupId = "auth-service")
    public void handleUserUpdate(UserDTO updatedUser) {
        log.info("🗑️ 유저 정보 변경 감지 - Redis 캐시 삭제: {}", updatedUser.getUsername());
        authCacheService.deleteUserCache(updatedUser.getUsername());
    }
}
