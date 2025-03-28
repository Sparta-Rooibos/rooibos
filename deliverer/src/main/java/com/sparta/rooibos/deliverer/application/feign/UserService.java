package com.sparta.rooibos.deliverer.application.feign;

import com.sparta.rooibos.deliverer.infrastructure.feign.dto.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse getUserByMaster(UUID userId);
}
