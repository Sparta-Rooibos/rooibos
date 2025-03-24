package com.sparta.rooibos.user.infrastructure.redis;

import com.sparta.rooibos.user.application.dto.UserAuthDTO;
import com.sparta.rooibos.user.application.dto.UserStreamEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventProducer  {
    private final @Qualifier("userStreamRedisTemplate") RedisTemplate<String, UserStreamEvent> redisTemplate;

    private static final String STREAM_KEY = "user-events";
    private static final long MAX_STREAM_LENGTH = 1000L; // ✅ 유저 이벤트 최대 1000개 유지

    public void sendUserInfo(UserAuthDTO dto) {
        UserStreamEvent event = new UserStreamEvent("create", dto);
        addEventToStream(event);
    }

    public void sendUserDeleteInfo(String email) {
        UserStreamEvent event = new UserStreamEvent("delete", email);
        addEventToStream(event);
    }

    public void sendUserReportInfo(String email) {
        UserStreamEvent event = new UserStreamEvent("report", email);
        addEventToStream(event);
    }

    private void addEventToStream(UserStreamEvent event) {
        ObjectRecord<String, Object> record = ObjectRecord.create(STREAM_KEY, event);
        redisTemplate.opsForStream().add(record);
        redisTemplate.opsForStream().trim(STREAM_KEY, MAX_STREAM_LENGTH, true);
        log.debug("유저 이벤트 전송 완료: {}", event.eventType());
    }
}
