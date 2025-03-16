package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateOrderResponseDTO(
    UUID order_id,
    UUID request_client_id,
    UUID response_client_id,
    UUID product_id,
    UUID delivery_id,
    int quantity,
    String requirement,
    LocalDateTime updated_at
) {
    public UpdateOrderResponseDTO(Order order){
        this(
            order.getId(),
            order.getRequestClientId(),
            order.getResponseClientId(),
            order.getProductId(),
            order.getDeliveryId(),
            order.getQuantity(),
            order.getRequirement(),
            order.getUpdatedAt()
        );
    }
}
