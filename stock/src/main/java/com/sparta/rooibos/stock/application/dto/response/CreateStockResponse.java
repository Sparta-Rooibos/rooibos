package com.sparta.rooibos.stock.application.dto.response;

import com.sparta.rooibos.stock.domain.entity.Stock;

import java.util.UUID;


public record CreateStockResponse(UUID id, String hubId, String productId, int productQuantity) {

    public static CreateStockResponse from(Stock stock) {
        return new CreateStockResponse(stock.getId(),
                                       stock.getHubId(),
                                       stock.getProductId(),
                                       stock.getProductQuantity());
    }
}
