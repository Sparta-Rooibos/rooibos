package com.sparta.rooibus.delivery.application.dto.response;

import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import java.util.UUID;

public record DeleteDeliveryLogResponse(
    UUID deletedDeliveryLogId
) {

    public static DeleteDeliveryLogResponse from(DeliveryLog deliveryLog) {
        return new DeleteDeliveryLogResponse(deliveryLog.getId());
    }
}
