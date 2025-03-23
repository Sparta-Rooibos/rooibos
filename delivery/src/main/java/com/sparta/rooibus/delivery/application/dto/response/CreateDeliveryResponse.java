package com.sparta.rooibus.delivery.application.dto.response;

import com.sparta.rooibus.delivery.domain.entity.Delivery;
import java.util.UUID;

public record CreateDeliveryResponse(
    UUID deliveryID
) {
    public static CreateDeliveryResponse from(Delivery delivery) {
        return new CreateDeliveryResponse(delivery.getId());
    }
}
