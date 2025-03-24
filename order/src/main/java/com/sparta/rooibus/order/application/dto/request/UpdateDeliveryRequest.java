package com.sparta.rooibus.order.application.dto.request;

import java.util.UUID;

public record UpdateDeliveryRequest(
    UUID deliveryId,
    String status
) {

    public static UpdateDeliveryRequest of(UUID deliveryId, String status) {
        return new UpdateDeliveryRequest(deliveryId, status);
    }
}
