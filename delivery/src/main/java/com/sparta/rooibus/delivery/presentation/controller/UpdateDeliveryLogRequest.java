package com.sparta.rooibus.delivery.presentation.controller;

import java.util.UUID;

public record UpdateDeliveryLogRequest(
    UUID deliveryLogId,
    String status
) {

}
