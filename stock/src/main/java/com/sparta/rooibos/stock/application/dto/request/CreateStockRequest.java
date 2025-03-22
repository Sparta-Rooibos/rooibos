package com.sparta.rooibos.stock.application.dto.request;

import com.sparta.rooibos.stock.domain.entity.Stock;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateStockRequest(@NotNull String hubId, @NotNull String productId, @PositiveOrZero int productQuantity) {

    public Stock toEntity() {
        return Stock.create(
                hubId,
                productId,
                productQuantity
        );
    }
}
