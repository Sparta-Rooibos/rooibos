package com.sparta.rooibus.delivery.application.dto.request;

import java.util.UUID;

public record UpdateDeliveryLogRequest(
    UUID deliveryLogId,
    String status
) {

}
