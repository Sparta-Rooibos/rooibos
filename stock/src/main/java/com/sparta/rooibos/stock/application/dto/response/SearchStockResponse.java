package com.sparta.rooibos.stock.application.dto.response;

import com.sparta.rooibos.stock.domain.entity.Pagination;
import com.sparta.rooibos.stock.domain.entity.Stock;

import java.util.List;


public record SearchStockResponse(List<SearchStockListResponse> stocks, long totalCount, long page, long size) {
    public static SearchStockResponse from(Pagination<Stock> stock) {
        return new SearchStockResponse(
                stock.getContent().stream().map(SearchStockListResponse::from).toList(),
                stock.getTotal(),
                stock.getPage(),
                stock.getSize()

        );
    }
}
