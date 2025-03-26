package com.sparta.rooibos.user.application.dto.response;

import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.entity.Role;
import com.sparta.rooibos.user.domain.entity.UserRoleStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String slackAccount,
        String phone,
        Role role,
        boolean hidden,
        UserRoleStatus status,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getSlackAccount(),
                user.getPhone(),
                user.getRole(),
                user.isHidden(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy(),
                user.getDeletedAt(),
                user.getDeletedBy()

        );
    }
}
