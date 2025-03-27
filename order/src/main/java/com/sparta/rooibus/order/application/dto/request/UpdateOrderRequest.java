package com.sparta.rooibus.order.application.dto.request;

import com.sparta.rooibus.order.domain.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateOrderRequest(
    @NotNull UUID id,
    OrderStatus status
) {
}
