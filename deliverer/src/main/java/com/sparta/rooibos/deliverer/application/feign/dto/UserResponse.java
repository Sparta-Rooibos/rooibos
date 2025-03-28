package com.sparta.rooibos.deliverer.application.feign.dto;

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
        String deletedBy) {
}
