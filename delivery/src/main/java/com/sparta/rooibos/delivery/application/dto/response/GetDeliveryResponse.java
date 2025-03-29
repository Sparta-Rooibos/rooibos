package com.sparta.rooibos.delivery.application.dto.response;

import com.sparta.rooibos.delivery.domain.entity.Delivery;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetDeliveryResponse(
    UUID deliveryId,
    String status,
    UUID departure,
    UUID arrival,
    String address,
    UUID recipient,
    String slackAccount,
    UUID deliverId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static GetDeliveryResponse from(Delivery delivery) {
        return new GetDeliveryResponse(
            delivery.getId(),
            delivery.getStatus().toString(),
            delivery.getDeparture(),
            delivery.getArrival(),
            delivery.getAddress(),
            delivery.getRecipient(),
            delivery.getSlackAccount(),
            delivery.getClientDeliver(),
            delivery.getCreatedAt(),
            delivery.getUpdatedAt()
        );
    }
}
