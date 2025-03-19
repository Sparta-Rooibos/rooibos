package com.sparta.rooibus.order.application.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record DeliveryResponseDTO(
    @NotNull UUID deliveryID        // 배송 ID
) {
}
