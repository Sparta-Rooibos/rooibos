package com.sparta.rooibus.order.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateOrderRequestDTO(
    @NotNull UUID requestClientId,
    @NotNull UUID responseClientId,
    @NotNull UUID productId,
    @Min(1) int quantity,
    String requirement
) {
}
