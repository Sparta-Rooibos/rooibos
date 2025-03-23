package com.sparta.rooibus.delivery.domain.entity.command;

import com.sparta.rooibus.delivery.domain.entity.Delivery;
import java.util.UUID;

public record DeliveryCommand(
    UUID departure,
    UUID arrival,
    String address,
    UUID recipient,
    String slackAccount,
    UUID orderId,
    UUID deliverId
) {
    public Delivery toDelivery(){
        return new Delivery(
            this.departure,
            this.arrival,
            this.address,
            this.recipient,
            this.orderId,
            this.slackAccount,
            this.deliverId
        );
    }
}
