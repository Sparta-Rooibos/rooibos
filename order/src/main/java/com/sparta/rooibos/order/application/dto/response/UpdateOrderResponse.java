package com.sparta.rooibos.order.application.dto.response;

import com.sparta.rooibos.order.domain.entity.Order;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateOrderResponse(
    UUID order_id,
    UUID request_client_id,
    UUID response_client_id,
    UUID product_id,
    UUID delivery_id,
    int quantity,
    String requirement,
    LocalDateTime updated_at
) {
    public static UpdateOrderResponse from(Order order) {

        return new UpdateOrderResponse(
            order.getId(),
            order.getRequestClientId(),
            order.getReceiveClientId(),
            order.getProductId(),
            order.getDeliveryId(),
            order.getQuantity(),
            order.getRequirement(),
            order.getUpdatedAt()
        );
    }
}
