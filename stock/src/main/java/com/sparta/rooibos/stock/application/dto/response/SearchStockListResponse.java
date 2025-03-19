package com.sparta.rooibos.stock.application.dto.response;

import com.sparta.rooibos.stock.domain.entity.Stock;

import java.util.UUID;

public record SearchStockListResponse(UUID id, String hubId, String productId, boolean isDeleted) {
    public static SearchStockListResponse from(Stock stock) {
        return new SearchStockListResponse(stock.getId(), stock.getHubId(), stock.getProductId(), stock.getDeletedBy() != null);
    }
}
