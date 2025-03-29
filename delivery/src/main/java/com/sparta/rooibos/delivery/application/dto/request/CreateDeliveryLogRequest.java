package com.sparta.rooibos.delivery.application.dto.request;

import java.util.UUID;

public record CreateDeliveryLogRequest(
    UUID deliveryId,
    UUID departure,
    String departureName,
    UUID arrival,
    String arrivalName,
    int sequence,
    String expectedDistance,
    String expectedTime,
    UUID deliverId
) {

}
