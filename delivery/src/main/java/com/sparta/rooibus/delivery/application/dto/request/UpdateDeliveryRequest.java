package com.sparta.rooibus.delivery.application.dto.request;

import java.util.UUID;

public record UpdateDeliveryRequest(
    UUID departure,
    UUID arrival,
    String address,
    UUID recipient,
    String slackAccount,
    UUID deliverId,
    UUID deliveryId,
    String status
) {

}
