package com.sparta.rooibos.auth.application.service.port;

import com.sparta.rooibos.auth.application.dto.UserAuthDTO;

import java.util.Optional;

public interface RedisProvider {
    void createUserInfo(UserAuthDTO userAuthDTO);
    Optional<UserAuthDTO> getUserInfo(String email);
    void deleteUserInfo(String email);
}
