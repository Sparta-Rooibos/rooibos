package com.spring.cloud.client.auth.application.service;

import com.spring.cloud.client.auth.application.dto.UserDTO;

import java.util.Optional;

public interface RedisProvider {
    void createUserInfo(UserDTO userDTO);
    Optional<UserDTO> getUserInfo(String email);
    void deleteUserInfo(String email);
    void addToBlacklist(String token);
    boolean isTokenBlacklisted(String token);
}
