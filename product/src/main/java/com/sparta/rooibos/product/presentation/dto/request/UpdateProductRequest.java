package com.sparta.rooibos.product.presentation.dto.request;


import com.sparta.rooibos.product.application.dto.request.UpdateProductApplicationRequest;

import java.util.UUID;

public record UpdateProductRequest(String name) {
    public UpdateProductApplicationRequest toApplication(UUID productId) {
        return new UpdateProductApplicationRequest(productId, name);
    }
}
