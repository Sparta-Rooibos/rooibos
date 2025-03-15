package com.sparta.rooibus.order.application.dto.request;

import com.sparta.rooibus.order.domain.entity.Order;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateOrderResponseDTO(
    UUID orderId,
    UUID requestClientId,   // 공급 업체 ID
    UUID responseClientId,  // 수령 업체 ID
    UUID productId,         // 상품 ID
    UUID deliveryId,
    int quantity,            // 수량
    String requirement,
    LocalDateTime updatedAt
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
