package com.sparta.rooibus.order.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateOrderRequest(
    @NotNull UUID id,
    UUID requestClientId,
    UUID receiveClientId,
    UUID productId,
    @Min(1) Integer quantity,
    String requirement
) {
}
