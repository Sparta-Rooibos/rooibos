package com.sparta.rooibos.stock.application.dto.request;


import com.sparta.rooibos.stock.domain.dto.criteria.StockPageCriteria;

import java.util.UUID;

public record SearchStockRequest(UUID id, String productId, String hubId, Boolean isDeleted, String sort, Integer page, Integer size) {

    public SearchStockRequest {
        sort = sort == null ? "createdAt" : sort;
        page = page == null ? 1 : page;
        size = size == null || size == 10 || size == 30 || size == 50 ? 10 : size;
    }

    public StockPageCriteria toCommand() {
        return new StockPageCriteria(id, productId, hubId, isDeleted,  sort, page, size);
    }
}
