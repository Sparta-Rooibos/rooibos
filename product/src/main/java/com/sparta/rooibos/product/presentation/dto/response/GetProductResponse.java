package com.sparta.rooibos.product.presentation.dto.response;

import com.sparta.rooibos.product.application.dto.response.GetProductApplicationResponse;

import java.time.LocalDateTime;
import java.util.UUID;
public record GetProductResponse(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public GetProductResponse(GetProductApplicationResponse response) {
        this(response.id(), response.name(), response.createdAt(), response.updatedAt());
    }

}
