package com.sparta.rooibos.product.application.dto.response;

import com.sparta.rooibos.product.domain.entity.Product;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetProductResponse(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public GetProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getCreateAt(), product.getUpdateAt());
    }
}
