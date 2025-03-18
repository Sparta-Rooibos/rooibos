package com.spring.cloud.client.auth.application.service.port;

import com.spring.cloud.client.auth.application.dto.UserDTO;

import java.util.Optional;

public interface RedisProvider {
    void createUserInfo(UserDTO userDTO);
    Optional<UserDTO> getUserInfo(String email);
    void deleteUserInfo(String email);
    void addToBlacklist(String email);
    boolean isTokenBlacklisted(String email);
}
