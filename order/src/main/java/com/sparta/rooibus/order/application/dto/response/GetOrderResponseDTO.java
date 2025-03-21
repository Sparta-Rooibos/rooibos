package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetOrderResponseDTO(
    UUID order_id,
    UUID request_client_id,
    UUID response_client_id,
    UUID product_id,
    OrderStatus status,
    UUID delivery_id,
    LocalDateTime created_at,
    LocalDateTime updated_at
) {
    public GetOrderResponseDTO(Order order){
        this(
            order.getId(),
            order.getRequestClientId(),
            order.getResponseClientId(),
            order.getProductId(),
            order.getStatus(),
            order.getDeliveryId(),
            order.getCreatedAt(),
            order.getUpdatedAt()
            );
    }
}
