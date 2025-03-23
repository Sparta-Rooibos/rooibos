package com.sparta.rooibos.product.application.dto.response;

import com.sparta.rooibos.product.application.feign.dto.response.ManageHubResponse;
import com.sparta.rooibos.product.domain.entity.Product;
import com.sparta.rooibos.product.domain.feign.dto.ManageHub;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetProductResponse(UUID id, String name, ManageHubResponse hub, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public GetProductResponse(Product product, ManageHub hub) {
        this(product.getId(), product.getName(), new ManageHubResponse(hub), product.getCreateAt(), product.getUpdateAt());
    }
}
