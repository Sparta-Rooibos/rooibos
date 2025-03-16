package com.sparta.rooibos.product.application.dto.response;

import com.sparta.rooibos.product.domain.entity.Product;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetProductApplicationResponse(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public GetProductApplicationResponse(Product product) {
        this(product.getId(), product.getName(), product.getCreateAt(), product.getUpdateAt());
    }
}
