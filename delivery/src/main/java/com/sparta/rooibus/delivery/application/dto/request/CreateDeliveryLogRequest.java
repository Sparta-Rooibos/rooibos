package com.sparta.rooibus.delivery.application.dto.request;

import java.util.UUID;

public record CreateDeliveryLogRequest(
    UUID deliveryId,
    UUID departure,
    UUID arrival,
    String sequence,
    String expectedDistance,
    String expectedTime,
    UUID deliverId
) {

}
