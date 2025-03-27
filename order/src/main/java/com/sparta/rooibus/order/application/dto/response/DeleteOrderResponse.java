package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;

import java.util.UUID;

public record DeleteOrderResponse(
    UUID deleted_order_id
) {
    public DeleteOrderResponse(Order order){
        this(order.getId());
    }

    public static DeleteOrderResponse from(Order targetOrder) {
        return new DeleteOrderResponse(targetOrder.getId());
    }
}
