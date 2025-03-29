package com.sparta.rooibos.order.application.dto.response;

import com.sparta.rooibos.order.domain.entity.Order;

import java.util.UUID;

public record CreateOrderResponse(
    UUID orderId,
    UUID deliveryId
) {

    public static CreateOrderResponse from(Order order) {
        return new CreateOrderResponse(order.getId(), order.getDeliveryId());
    }
}
