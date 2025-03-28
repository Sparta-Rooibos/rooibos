package com.sparta.rooibos.order.application.dto.request;

import com.sparta.rooibos.order.domain.entity.Order;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateDeliveryRequest(
    @NotNull UUID orderID,
    @NotNull UUID requestClientId,
    @NotNull UUID receiveClientId
) {

    public static CreateDeliveryRequest from(Order order) {
        return new CreateDeliveryRequest(
            order.getId(),
            order.getRequestClientId(),
            order.getReceiveClientId()
        );
    }
}
