package com.sparta.rooibos.stock.application.dto.request;

import com.sparta.rooibos.stock.domain.entity.Stock;

public record CreateStockRequest(String hubId, String productId, int productQuantity) {

    public Stock toEntity() {
        return Stock.create(
                hubId,
                productId,
                productQuantity
        );
    }
}
