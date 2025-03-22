package com.sparta.rooibus.delivery.application.dto.response;

import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import java.util.UUID;

public record CreateDeliveryLogResponse(UUID deliveryId) {

    public static CreateDeliveryLogResponse from(DeliveryLog deliveryLog) {
        return new CreateDeliveryLogResponse(deliveryLog.getId());
    }
}
