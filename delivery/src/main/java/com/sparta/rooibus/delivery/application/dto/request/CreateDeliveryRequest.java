package com.sparta.rooibus.delivery.application.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateDeliveryRequest(
    @NotNull UUID orderId,
    @NotNull UUID requestClientId,
    @NotNull UUID receiveClientId
) {
}
