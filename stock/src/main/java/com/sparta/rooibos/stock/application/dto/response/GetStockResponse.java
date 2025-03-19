package com.sparta.rooibos.stock.application.dto.response;

import com.sparta.rooibos.stock.domain.entity.Stock;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetStockResponse(
         UUID id,
         String hubId,
         String productId,
         int productQuantity,

         String createdBy,
         LocalDateTime createdAt,

         String updatedBy,
         LocalDateTime updatedAt,

         String deletedBy,
         LocalDateTime deletedAt

) {
    public static GetStockResponse from(Stock stock) {
        return new GetStockResponse(stock.getId(),
                stock.getHubId(),
                stock.getProductId(),
                stock.getProductQuantity(),
                stock.getCreatedBy(),
                stock.getCreatedAt(),
                stock.getUpdatedBy(),
                stock.getUpdatedAt(),
                stock.getDeletedBy(),
                stock.getDeletedAt());
    }
}
