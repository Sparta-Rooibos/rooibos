package com.sparta.rooibus.order.application.dto.response;

import java.util.UUID;

public record CreateDeliveryResponse(
    UUID deliveryId,
    String status,
    UUID departure,
    UUID arrival,
    String address,
    UUID recipient,
    String slackAccount,
    UUID deliverId
) {

}
