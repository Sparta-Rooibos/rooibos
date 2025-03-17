package com.sparta.rooibus.delivery.application.dto.response;

import com.sparta.rooibus.delivery.domain.entity.Delivery;

public record CreateDeliveryResponse(
    String message
) {

    public static CreateDeliveryResponse from(Delivery delivery) {
        return new CreateDeliveryResponse("배송(" + delivery.getId() + ")이 생성되었습니다.");
    }
}
