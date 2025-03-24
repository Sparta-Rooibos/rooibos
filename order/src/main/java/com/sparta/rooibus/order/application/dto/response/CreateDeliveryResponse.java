package com.sparta.rooibus.order.application.dto.response;

import java.util.UUID;

public record CreateDeliveryResponse(
    UUID deliveryId,
    UUID departure
) {

}
