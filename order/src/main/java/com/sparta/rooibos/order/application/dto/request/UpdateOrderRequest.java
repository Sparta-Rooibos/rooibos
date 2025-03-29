package com.sparta.rooibos.order.application.dto.request;

import com.sparta.rooibos.order.domain.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateOrderRequest(
    @NotNull UUID id,
    OrderStatus status
) {
}
