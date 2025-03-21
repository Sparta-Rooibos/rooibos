package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;
import java.time.LocalDateTime;
import java.util.UUID;

public record SearchOrderResponseDTO(
    UUID id,
    UUID requestClientId,
    UUID responseClientId,
    UUID productId,
    int quantity,
    String requirement,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public SearchOrderResponseDTO(Order order) {
        this(order.getId(),
        order.getRequestClientId(),
        order.getResponseClientId(),
        order.getProductId(),
        order.getQuantity(),
        order.getRequirement(),
        order.getStatus().toString(),
        order.getCreatedAt(),
        order.getUpdatedAt()
        );
    }
}
