package com.sparta.rooibos.auth.application.service.port;

import java.util.Optional;

public interface RedisProvider {
    void createUserInfo(UserAuthDTO userAuthDTO);
    Optional<UserAuthDTO> getUserInfo(String email);
    void deleteUserInfo(String email);
}
