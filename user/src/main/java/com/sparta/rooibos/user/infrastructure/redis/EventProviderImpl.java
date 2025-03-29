package com.sparta.rooibos.user.infrastructure.redis;

import com.sparta.rooibos.user.application.dto.UserAuthDTO;
import com.sparta.rooibos.user.application.service.port.EventProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventProviderImpl implements EventProvider {
    private final BlacklistManager blacklistManager;
    private final UserEventProducer userEventProducer;

    @Override
    public void sendUserInfo(UserAuthDTO dto) {
        userEventProducer.sendUserInfo(dto);
    }

    @Override
    public void sendUserDeleteInfo(String email) {
        userEventProducer.sendUserDeleteInfo(email);
    }

    @Override
    public void sendUserReportInfo(String email) {
        userEventProducer.sendUserReportInfo(email);
    }

    @Override
    public void blacklistUser(String email, long ttlSeconds) {
        blacklistManager.blacklistUser(email, ttlSeconds);
    }
}

