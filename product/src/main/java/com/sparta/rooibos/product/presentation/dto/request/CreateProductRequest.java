package com.sparta.rooibos.product.presentation.dto.request;

import com.sparta.rooibos.product.application.dto.request.CreateProductApplicationRequest;


public record CreateProductRequest(String name, String clientId, String managedHubId) {
    public CreateProductApplicationRequest toApplication() {
        return new CreateProductApplicationRequest(name, clientId, managedHubId);
    }
}
