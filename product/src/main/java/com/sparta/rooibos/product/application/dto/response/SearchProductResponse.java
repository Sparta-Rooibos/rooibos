package com.sparta.rooibos.product.application.dto.response;


import java.util.List;

public record SearchProductResponse(List<SearchProductListResponse> products, long totalCount, long page, long size) {
}
