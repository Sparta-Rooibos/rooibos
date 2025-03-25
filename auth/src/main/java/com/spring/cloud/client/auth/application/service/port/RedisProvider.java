package com.spring.cloud.client.auth.application.service.port;

import com.spring.cloud.client.auth.application.dto.UserAuthDTO;

import java.util.Optional;

public interface RedisProvider {
    void createUserInfo(UserAuthDTO userAuthDTO);
    Optional<UserAuthDTO> getUserInfo(String email);
    void deleteUserInfo(String email);
}
