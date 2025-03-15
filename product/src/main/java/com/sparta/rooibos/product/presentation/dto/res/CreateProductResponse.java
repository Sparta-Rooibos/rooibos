package com.sparta.rooibos.product.presentation.dto.res;

import com.sparta.rooibos.product.application.dto.res.CreateProductApplicationResponse;

import java.util.UUID;

public record CreateProductResponse(UUID productId, String name) {
    public CreateProductResponse(CreateProductApplicationResponse response) {
        this(response.productId(), response.name());
    }
}
