package com.spring.cloud.client.auth.application.service;

import com.spring.cloud.client.auth.application.dto.UserDTO;

import java.util.Optional;

public interface RedisProvider {
    Optional<UserDTO> getUserInfo(String username);
    void createUserInfo(UserDTO user);
    void deleteUserCache(String username);
    void addToBlacklist(String token);
    boolean isTokenBlacklisted(String token);
}
