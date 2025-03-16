package com.sparta.rooibos.product.presentation.dto.response;

import com.sparta.rooibos.product.application.dto.response.CreateProductResponse;

import java.util.UUID;

public record CreateProductResponseDto(UUID productId, String name) {
    public CreateProductResponseDto(CreateProductResponse response) {
        this(response.productId(), response.name());
    }
}
