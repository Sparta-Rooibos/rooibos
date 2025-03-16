package com.sparta.rooibos.product.presentation.dto.response;


import com.sparta.rooibos.product.application.dto.response.SearchProductApplicationResponse;

import java.util.List;

public record SearchProductResponse(List<SearchProductListResponse> products, long totalCount, long page, long size) {
    public SearchProductResponse(SearchProductApplicationResponse response) {
        this(response.products().stream().map(product -> new SearchProductListResponse(product.id(), product.name(), product.name(), product.managedHubId())).toList(),
                response.totalCount(), response.page(), response.size());
    }
}
