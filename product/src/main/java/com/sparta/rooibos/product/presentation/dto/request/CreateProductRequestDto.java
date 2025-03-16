package com.sparta.rooibos.product.presentation.dto.request;

import com.sparta.rooibos.product.application.dto.request.CreateProductRequest;


public record CreateProductRequestDto(String name, String clientId, String managedHubId) {
    public CreateProductRequest toApplication() {
        return new CreateProductRequest(name, clientId, managedHubId);
    }
}
