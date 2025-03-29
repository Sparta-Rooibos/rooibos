package com.sparta.rooibos.order.application.dto.response;

import java.util.UUID;

public record CreateDeliveryResponse(
    UUID deliveryId,
    UUID departure
) {

}
