package com.sparta.rooibos.user.application.service.port;

import com.sparta.rooibos.user.application.dto.UserStreamRequest;

public interface EventProvider {
    void sendUserInfo(UserStreamRequest request);
    void sendUserDeleteInfo(String email);
    void sendUserReportInfo(String email);
    void blacklistUser(String email, long ttlSeconds);
}