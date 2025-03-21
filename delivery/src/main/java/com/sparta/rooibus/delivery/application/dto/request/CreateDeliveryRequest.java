package com.sparta.rooibus.delivery.application.dto.request;

import com.sparta.rooibus.delivery.domain.entity.command.DeliveryCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateDeliveryRequest(
    @NotNull UUID departure,
    @NotNull UUID arrival,
    @NotNull UUID orderId,
    @NotBlank String address,
    @NotNull UUID recipient
) {

    // DeliveryCommand로 변환하는 메서드
    public DeliveryCommand toCommand(String slackAccount,UUID deliverId) {
        return new DeliveryCommand(
            this.departure(),
            this.arrival(),
            this.address(),
            this.recipient(),
            slackAccount,
            this.orderId(),
            deliverId
        );
    }
}
