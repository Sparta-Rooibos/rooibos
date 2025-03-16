package com.sparta.rooibos.product.presentation.dto.response;


import com.sparta.rooibos.product.application.dto.response.SearchProductResponse;

import java.util.List;

public record SearchProductResponseDto(List<SearchProductListResponseDto> products, long totalCount, long page, long size) {
    public SearchProductResponseDto(SearchProductResponse response) {
        this(response.products().stream().map(product -> new SearchProductListResponseDto(product.id(), product.name(), product.name(), product.managedHubId())).toList(),
                response.totalCount(), response.page(), response.size());
    }
}
