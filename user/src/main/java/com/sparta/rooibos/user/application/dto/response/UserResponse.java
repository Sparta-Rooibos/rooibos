package com.sparta.rooibos.user.application.dto.response;

import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.entity.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String slackAccount,
        String phone,
        Role role
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getSlackAccount(),
                user.getPhone(),
                user.getRole()
        );
    }
}
