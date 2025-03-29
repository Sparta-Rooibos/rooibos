package com.sparta.rooibos.delivery.application.dto.response;

import com.sparta.rooibos.delivery.domain.entity.DeliveryLog;

import java.util.UUID;

public record DeleteDeliveryLogResponse(
    UUID deletedDeliveryLogId
) {

    public static DeleteDeliveryLogResponse from(DeliveryLog deliveryLog) {
        return new DeleteDeliveryLogResponse(deliveryLog.getId());
    }
}
