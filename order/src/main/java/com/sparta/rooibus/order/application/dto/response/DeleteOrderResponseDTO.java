package com.sparta.rooibus.order.application.dto.response;

import com.sparta.rooibus.order.domain.entity.Order;
import java.util.UUID;

public record DeleteOrderResponseDTO(
    UUID deleted_order_id
) {
    public DeleteOrderResponseDTO(Order order){
        this(order.getId());
    }
}
