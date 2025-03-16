package com.sparta.rooibos.product.presentation.dto.response;

import com.sparta.rooibos.product.application.dto.response.GetProductResponse;

import java.time.LocalDateTime;
import java.util.UUID;
public record GetProductResponseDto(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public GetProductResponseDto(GetProductResponse response) {
        this(response.id(), response.name(), response.createdAt(), response.updatedAt());
    }

}
