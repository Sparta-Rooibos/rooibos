package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateOrderResponseDTO(
    UUID order_id,            // 주문 ID
    UUID request_client_id,   // 공급 업체 ID
    UUID response_client_id,  // 수령 업체 ID
    UUID product_id,          // 상품 ID
    UUID delivery_id,         // 배송 ID
    int quantity,             // 수량
    String requirement,       // 요구사항
    LocalDateTime updated_at  // 수정일
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
