package com.sparta.rooibus.order.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateOrderRequestDTO(
    @NotNull UUID id,
    UUID requestClientId,
    UUID responseClientId,
    UUID productId,
    @Min(1) Integer quantity,
    String requirement
) {

}
