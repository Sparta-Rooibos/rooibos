package com.spring.cloud.client.auth.infrastructure.redis;

import com.spring.cloud.client.auth.application.dto.AuthStreamRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;

import static com.spring.cloud.client.auth.application.dto.AuthStreamMapper.toResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumerListener implements StreamListener<String, ObjectRecord<String, AuthStreamRequest>> {
    private final StreamMessageListenerContainer<String, ObjectRecord<String, AuthStreamRequest>> listenerContainer;
    private final RedisTemplate<String, AuthStreamRequest> redisTemplate;
    private final AuthCacheService authCacheService;

    private static final String STREAM_KEY = "user-events";
    private static final String CONSUMER_GROUP = "auth-service-group";
    private static final String CONSUMER_NAME = "auth-service";

    @PostConstruct
    public void init() {
        createConsumerGroup();

        listenerContainer.receive(
                Consumer.from(CONSUMER_GROUP, CONSUMER_NAME),
                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()),
                this
        );

        listenerContainer.start();
        log.info("Redis 스트림 리스너 시작: {}", CONSUMER_GROUP);
    }


    @Override
    public void onMessage(ObjectRecord<String, AuthStreamRequest> message) {
        log.info("🔥 onMessage() 호출됨");
        AuthStreamRequest event = message.getValue();
        if (event == null) return;

        log.info("유저 정보 수신: {}", event);

        switch (event.eventType()) {
            case "create" -> authCacheService.createUserInfo(toResponse(event));
            case "delete" -> authCacheService.deleteUserInfo(event.email());
            default -> log.warn("알 수 없는 이벤트 타입: {}", event.eventType());
        }

        // 메시지 처리 완료 후 ACK → XPENDING에서 제거됨
        redisTemplate.opsForStream().acknowledge(STREAM_KEY, CONSUMER_GROUP, message.getId());
        redisTemplate.opsForStream().trim(STREAM_KEY, 1000);

        com.spring.cloud.client.auth.application.dto.AuthStreamResponse cached =
                authCacheService.getUserInfo(event.email()).orElse(null);
        System.out.println("✅ 캐시 확인: " + cached);
    }

    private void createConsumerGroup() {
        try {
            redisTemplate.opsForStream().createGroup(STREAM_KEY, ReadOffset.from("0"), CONSUMER_GROUP);
            log.info("Consumer Group 생성됨: {}", CONSUMER_GROUP);
        } catch (Exception e) {
            log.warn("Consumer Group 이미 존재하거나 생성 실패: {}", e.getMessage());
        }
    }
}
