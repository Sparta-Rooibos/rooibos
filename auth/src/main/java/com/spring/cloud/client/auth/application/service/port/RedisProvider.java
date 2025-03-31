package com.spring.cloud.client.auth.application.service.port;

import com.spring.cloud.client.auth.application.dto.CachedUserResponse;

import java.util.Optional;

public interface RedisProvider {
    void createUserInfo(CachedUserResponse authStreamResponse);
    Optional<CachedUserResponse> getUserInfo(String email);
    void deleteUserInfo(String email);
}
