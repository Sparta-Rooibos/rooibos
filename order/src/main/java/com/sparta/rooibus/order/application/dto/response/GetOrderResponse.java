package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;

import java.util.UUID;

public record GetOrderResponse(
    UUID order_id,
    UUID requestClientId,
    UUID receiveClientId,
    UUID productId,
    UUID deliveryId,
    String createdAt,
    String updatedAt
) {
    public static GetOrderResponse from(Order order) {

        return new GetOrderResponse(
            order.getId(),
            order.getRequestClientId(),
            order.getReceiveClientId(),
            order.getProductId(),
            order.getDeliveryId(),
            order.getCreatedAt().toString(),
            order.getUpdatedAt().toString()
        );
    }
}
