package com.sparta.rooibos.product.application.dto.request;

import java.util.UUID;

public record SearchProductRequest(UUID id, String name, Boolean isDeleted, Integer page, Integer size, String sort) {
    public SearchProductRequest {
        sort = sort == null ? "createdAt" : sort;
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
    }
}
