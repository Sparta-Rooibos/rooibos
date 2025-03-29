package com.sparta.rooibos.delivery.application.dto.response;

import com.sparta.rooibos.delivery.domain.entity.Delivery;

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

    public static UpdateDeliveryResponse from(Delivery delivery) {
        return new UpdateDeliveryResponse(
            delivery.getId(),
            delivery.getStatus().toString(),
            delivery.getDeparture(),
            delivery.getArrival(),
            delivery.getAddress(),
            delivery.getRecipient(),
            delivery.getSlackAccount(),
            delivery.getClientDeliver(),
            delivery.getUpdatedAt(),
            delivery.getUpdatedBy()
        );
    }
}
