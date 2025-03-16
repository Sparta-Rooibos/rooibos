package com.sparta.rooibus.order.application.dto.request;

import com.sparta.rooibus.order.domain.entity.Order;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DeliveryRequestDTO(
    @NotNull UUID orderID,
    @NotNull UUID requestClientId,
    @NotNull UUID responseClientId,
    @NotNull UUID productId
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
