package com.sparta.rooibos.product.presentation.dto.request;


import com.sparta.rooibos.product.application.dto.request.SearchProductRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
public record SearchProductRequestDto(UUID id, String name, String sort, Integer page, Integer size) {
    public SearchProductRequestDto {
        sort = sort == null ? "createAt" : sort;
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
    }

    public SearchProductRequest toApplication(Pageable pageable) {
        return new SearchProductRequest(id,name,pageable);
    }
}
