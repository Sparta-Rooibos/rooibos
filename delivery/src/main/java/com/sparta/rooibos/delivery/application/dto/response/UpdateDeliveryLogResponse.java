package com.sparta.rooibos.delivery.application.dto.response;

import com.sparta.rooibos.delivery.domain.entity.DeliveryLog;

import java.util.UUID;

public record UpdateDeliveryLogResponse(
    UUID id,
    UUID deliveryId,
    UUID departure,
    UUID arrival,
    int sequence,
    String expectedDistance,
    String expectedTime,
    String takenDistance,
    String takenTime,
    String status,
    UUID deliver
) {
    public static UpdateDeliveryLogResponse from(DeliveryLog deliveryLog) {
        return new UpdateDeliveryLogResponse(
            deliveryLog.getId(),
            deliveryLog.getDeliveryId(),
            deliveryLog.getFromHubId(),
            deliveryLog.getToHubId(),
            deliveryLog.getSequence(),
            deliveryLog.getExpectedDistance(),
            deliveryLog.getExpectedTime(),
            deliveryLog.getTakenDistance(),
            deliveryLog.getTakenTime(),
            deliveryLog.getStatus().toString(),
            deliveryLog.getDeliver()
        );
    }
}
