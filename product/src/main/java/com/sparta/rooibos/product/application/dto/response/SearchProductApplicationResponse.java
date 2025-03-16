package com.sparta.rooibos.product.application.dto.response;


import java.util.List;

public record SearchProductApplicationResponse(List<SearchProductApplicationListResponse> products, long totalCount, long page, long size) {
}
