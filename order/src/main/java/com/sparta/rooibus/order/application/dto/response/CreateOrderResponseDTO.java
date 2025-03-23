package com.sparta.rooibus.order.application.dto.response;

import java.util.UUID;

public record CreateOrderResponseDTO(
    UUID orderId,
    UUID deliveryId
) {

}
