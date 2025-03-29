package com.sparta.rooibos.deliverer.application.dto.response;

import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import com.sparta.rooibos.deliverer.domain.entity.DelivererType;

import java.time.LocalDateTime;
import java.util.UUID;

public record DelivererResponse(
        UUID id,
        String username,
        String email,
        String phone,
        String slackAccount,
        UUID hubId,
        DelivererType type,
        int order,
        LocalDateTime createdAt,
        String createBy,
        LocalDateTime updatedAt,
        String updatedBy,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static DelivererResponse from(Deliverer deliverer) {
        return new DelivererResponse(
                deliverer.getId(),
                deliverer.getUsername(),
                deliverer.getEmail(),
                deliverer.getPhone(),
                deliverer.getSlackAccount(),
                deliverer.getHubId(),
                deliverer.getType(),
                deliverer.getOrder(),
                deliverer.getCreatedAt(),
                deliverer.getCreatedBy(),
                deliverer.getUpdatedAt(),
                deliverer.getUpdatedBy(),
                deliverer.getDeletedAt(),
                deliverer.getDeletedBy()
        );
    }
}

