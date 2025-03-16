package com.spring.cloud.client.auth.infrastructure.kafka;

import com.spring.cloud.client.auth.application.dto.UserDTO;
import com.spring.cloud.client.auth.infrastructure.redis.AuthCacheService;
import com.spring.cloud.client.auth.infrastructure.redis.BlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthEventConsumer {
    private final AuthCacheService authCacheService;
    private final BlacklistService blacklistService;

    @KafkaListener(topics = "auth-service.user.synced", groupId = "auth-service")
    public void handleUser(UserDTO userDTO) {
        log.info("Kafka 응답 수신 - username: {}", userDTO);
        authCacheService.createUserInfo(userDTO);
    }

    @KafkaListener(topics = "auth-service.user.deleted", groupId = "auth-service")
    public void handleUserDelete(String email) {
        log.info("유저 삭제 감지 - Redis 데이터 제거: {}", email);
        authCacheService.deleteUserInfo(email);
    }

    @KafkaListener(topics = "auth-service.user.reported", groupId = "auth-service")
    public void handleUserReport(String email) {
        log.info("유저 신고 감지 - 블랙리스트 추가: {}", email);
//        blacklistService.blacklistRefreshToken(email);
//        blacklistService.blacklistAccessToken(email);
        authCacheService.deleteUserInfo(email);
    }
}
