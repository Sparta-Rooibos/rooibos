package com.spring.cloud.client.auth.application.service.port;

import com.spring.cloud.client.auth.application.dto.AuthStreamResponse;

import java.util.Optional;

public interface RedisProvider {
    void createUserInfo(AuthStreamResponse authStreamResponse);
    Optional<AuthStreamResponse> getUserInfo(String email);
    void deleteUserInfo(String email);
}
