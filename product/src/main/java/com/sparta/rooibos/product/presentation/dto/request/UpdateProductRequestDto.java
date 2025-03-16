package com.sparta.rooibos.product.presentation.dto.request;


import com.sparta.rooibos.product.application.dto.request.UpdateProductRequest;

import java.util.UUID;

public record UpdateProductRequestDto(String name) {
    public UpdateProductRequest toApplication(UUID productId) {
        return new UpdateProductRequest(productId, name);
    }
}
