package com.sparta.rooibos.delivery.application.dto.request;

import com.sparta.rooibos.delivery.domain.model.DeliveryStatus;

import java.util.UUID;

public record UpdateDeliveryRequest(
    UUID deliveryId,
    DeliveryStatus status
) {

}
