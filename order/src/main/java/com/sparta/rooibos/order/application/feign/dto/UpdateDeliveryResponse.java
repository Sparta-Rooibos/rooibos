package com.sparta.rooibos.order.application.feign.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateDeliveryResponse(
        UUID deliveryId,
        String status,
        UUID departure,
        UUID arrival,
        String address,
        UUID recipient,
        String slackAccount,
        UUID deliverId,
        LocalDateTime updatedAt,
        String updatedBy
) {
}
