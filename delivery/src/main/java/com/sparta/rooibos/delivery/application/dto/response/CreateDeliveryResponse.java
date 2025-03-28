package com.sparta.rooibos.delivery.application.dto.response;

import com.sparta.rooibos.delivery.domain.entity.Delivery;

import java.util.UUID;

public record CreateDeliveryResponse(
    UUID deliveryID,
    UUID manageHubId
) {
    public static CreateDeliveryResponse from(Delivery delivery) {
        return new CreateDeliveryResponse(delivery.getId(),delivery.getDeparture());
    }
}
