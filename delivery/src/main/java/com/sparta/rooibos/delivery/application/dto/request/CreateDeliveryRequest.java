package com.sparta.rooibos.delivery.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateDeliveryRequest(
    @NotNull @JsonProperty("orderId") UUID orderId,
    @NotNull @JsonProperty("requestClientId") UUID requestClientId,
    @NotNull @JsonProperty("receiveClientId")UUID receiveClientId
) {
}
