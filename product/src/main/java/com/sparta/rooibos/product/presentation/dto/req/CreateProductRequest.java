package com.sparta.rooibos.product.presentation.dto.req;

import com.sparta.rooibos.product.application.dto.req.CreateProductApplicationRequest;


public record CreateProductRequest(String name, String clientId, String managedHubId) {
    public CreateProductApplicationRequest toApplication() {
        return new CreateProductApplicationRequest(name, clientId, managedHubId);
    }
}
