package com.sparta.rooibus.delivery.application.dto.request;

import com.sparta.rooibus.delivery.domain.model.DeliveryStatus;
import java.util.UUID;

public record UpdateDeliveryRequest(
    UUID deliveryId,
    DeliveryStatus status
) {

}
