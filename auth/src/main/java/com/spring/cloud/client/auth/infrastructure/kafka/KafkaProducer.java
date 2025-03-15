package com.spring.cloud.client.auth.infrastructure.kafka;


import com.spring.cloud.client.auth.application.dto.UserDTO;
import com.spring.cloud.client.auth.application.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer implements EventService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaResponseHandler responseHandler;

    public UserDTO requestUserInfo(String username) {
        String requestId = UUID.randomUUID().toString(); // ✅ requestId를 UUID로 변경
        CompletableFuture<UserDTO> future = responseHandler.createRequest(requestId);

        log.info("🔵 Kafka 요청 전송 - requestId: {}, username: {}", requestId, username);

        try {
            kafkaTemplate.send("user-service.auth.request", requestId, username);
            return future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            log.error("⏳ Kafka 응답 시간 초과 - username: {}, error: {}", username, e.getMessage());
            throw new RuntimeException("Kafka 응답 시간 초과", e);
        } catch (Exception e) {
            log.error("❌ Kafka 요청 실패 - username: {}, error: {}", username, e.getMessage());
            throw new RuntimeException("유저 정보 요청 실패", e);
        }
    }
}