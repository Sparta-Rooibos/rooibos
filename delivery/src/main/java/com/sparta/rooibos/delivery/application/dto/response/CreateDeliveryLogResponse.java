package com.sparta.rooibos.delivery.application.dto.response;

import com.sparta.rooibos.delivery.domain.entity.DeliveryLog;

import java.util.UUID;

public record CreateDeliveryLogResponse(UUID deliveryId) {

    public static CreateDeliveryLogResponse from(DeliveryLog deliveryLog) {
        return new CreateDeliveryLogResponse(deliveryLog.getId());
    }
}
