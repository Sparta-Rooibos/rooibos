package com.sparta.rooibus.delivery.presentation.controller;

import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import com.sparta.rooibus.delivery.domain.model.DeliveryLogEnum;
import com.sparta.rooibus.delivery.domain.model.DeliveryStatus;
import java.util.UUID;

public record GetDeliveryLogResponse(
    UUID id,
    UUID deliveryId,
    UUID departure,
    UUID arrival,
    String sequence,
    String expectedDistance,
    String expectedTime,
    String takenDistance,
    String takenTime,
    String status,
    UUID deliver
) {

    public static GetDeliveryLogResponse from(DeliveryLog deliveryLog) {
        return new GetDeliveryLogResponse(
            deliveryLog.getId(),
            deliveryLog.getDeliveryId(),
            deliveryLog.getDeparture(),
            deliveryLog.getArrival(),
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
