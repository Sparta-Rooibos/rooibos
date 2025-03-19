package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetOrderResponseDTO(
    UUID order_id,
    UUID request_client_id,
    UUID response_client_id,
    UUID product_id,
    UUID delivery_id,
    LocalDateTime created_at,
    LocalDateTime updated_at
) {
    public GetOrderResponseDTO(Order order){
        this(
            order.getId(),
            order.getRequestClientId(),
            order.getReceiveClientId(),
            order.getProductId(),
            order.getDeliveryId(),
            order.getCreatedAt(),
            order.getUpdatedAt()
            );
    }
}
