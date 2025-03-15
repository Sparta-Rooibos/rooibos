package com.sparta.rooibus.order.application.dto.request;

import com.sparta.rooibus.order.domain.entity.Order;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DeliveryRequestDTO(
    @NotNull UUID orderID,           // 주문 ID
    @NotNull UUID requestClientId,   // 공급 업체 ID
    @NotNull UUID responseClientId,  // 수령 업체 ID
    @NotNull UUID productId          // 상품 ID
) {
    public DeliveryRequestDTO(Order order) {
        this(
            order.getId(),
            order.getRequestClientId(),
            order.getResponseClientId(),
            order.getProductId()
        );
    }
}
