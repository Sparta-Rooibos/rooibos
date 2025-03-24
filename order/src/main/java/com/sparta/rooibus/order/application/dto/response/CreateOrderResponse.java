package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;
import java.util.UUID;

public record CreateOrderResponse(
    UUID orderId,
    UUID deliveryId
) {

    public static CreateOrderResponse from(Order order) {
        return new CreateOrderResponse(order.getId(), order.getDeliveryId());
    }
}
