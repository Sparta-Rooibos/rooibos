package com.sparta.rooibos.delivery.application.dto.response.feign.client;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetClientResponse(
    UUID id,
    String name,
    String address,
    String type,
    ManageHubResponse manageHub,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public record ManageHubResponse(
        UUID id,
        String name,
        String region,
        String address
    ) {}
}
