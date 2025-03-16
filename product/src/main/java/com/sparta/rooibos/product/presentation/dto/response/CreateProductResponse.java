package com.sparta.rooibos.product.presentation.dto.response;

import com.sparta.rooibos.product.application.dto.response.CreateProductApplicationResponse;

import java.util.UUID;

public record CreateProductResponse(UUID productId, String name) {
    public CreateProductResponse(CreateProductApplicationResponse response) {
        this(response.productId(), response.name());
    }
}
