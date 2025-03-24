package com.spring.cloud.client.auth.infrastructure.redis;

import com.spring.cloud.client.auth.application.dto.UserAuthDTO;
import com.spring.cloud.client.auth.application.dto.UserStreamEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumerListener implements StreamListener<String, ObjectRecord<String, UserStreamEvent>>, InitializingBean {
    private final RedisTemplate<String, UserStreamEvent> redisTemplate;
    private final AuthCacheService authCacheService;

    private static final String STREAM_KEY = "user-events";
    private static final String CONSUMER_GROUP = "auth-service-group";
    private static final String CONSUMER_NAME = "auth-service";

    private StreamMessageListenerContainer<String, ObjectRecord<String, UserStreamEvent>> listenerContainer;

    @Override
    public void afterPropertiesSet() {
        createConsumerGroup();

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, UserStreamEvent>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofMillis(500))
                        .targetType(UserStreamEvent.class)
                        .build();

        listenerContainer = StreamMessageListenerContainer.create(redisTemplate.getConnectionFactory(), options);
        listenerContainer.receive(Consumer.from(CONSUMER_GROUP, CONSUMER_NAME),
                StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()),
                this);

        listenerContainer.start();
        log.info("Redis 스트림 리스너 시작: {}", CONSUMER_GROUP);
    }

    @Override
    public void onMessage(ObjectRecord<String, UserStreamEvent> message) {
        UserStreamEvent event = message.getValue();
        if (event == null) return;

        log.info("유저 정보 수신: {}", event);

        switch (event.eventType()) {
            case "create" -> {
                if (event.payload() instanceof UserAuthDTO dto) {
                    authCacheService.createUserInfo(dto);
                } else {
                    log.warn("create 이벤트에 잘못된 payload 형식");
                }
            }
            case "delete" -> {
                if (event.payload() instanceof String email) {
                    authCacheService.deleteUserInfo(email);
                } else {
                    log.warn("delete 이벤트에 잘못된 payload 형식");
                }
            }
        }

        // 메시지 처리 완료 후 ACK → XPENDING에서 제거됨
        redisTemplate.opsForStream().acknowledge(STREAM_KEY, CONSUMER_GROUP, message.getId());
        redisTemplate.opsForStream().trim(STREAM_KEY, 1000);
    }

    private void createConsumerGroup() {
        try {
            if (!Boolean.TRUE.equals(redisTemplate.hasKey(STREAM_KEY))) {
                redisTemplate.opsForStream().createGroup(STREAM_KEY, ReadOffset.from("0"), CONSUMER_GROUP);
                log.info("Consumer Group 생성 완료: {}", CONSUMER_GROUP);
            }
        } catch (Exception e) {
            log.warn("Consumer Group 이미 존재하거나 생성 실패: {}", e.getMessage());
        }
    }
}
