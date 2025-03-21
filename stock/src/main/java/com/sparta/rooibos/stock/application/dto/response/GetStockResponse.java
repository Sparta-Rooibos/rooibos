package com.sparta.rooibos.stock.application.dto.response;

import com.sparta.rooibos.stock.domain.entity.Stock;

import java.util.UUID;

public record GetStockResponse(
         UUID id,
         String hubId,
         String productId,
         int productQuantity

) {
    public static GetStockResponse from(Stock stock) {
        return new GetStockResponse(stock.getId(),
                stock.getHubId(),
                stock.getProductId(),
                stock.getProductQuantity());
    }
}
