package com.sparta.rooibos.stock.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UpdateMultiStockRequest(
        @NotNull List<UpdateMultiStockListRequest> stocks
) {
    public List<UUID> asIds() {
        return stocks.stream().map(UpdateMultiStockListRequest::id).toList();
    }

    public int findProductQuantity(UUID id) {
        return stocks.stream().filter(stock -> stock.id().equals(id))
                .map(UpdateMultiStockListRequest::productQuantity)
                .findFirst().orElse(0);
    }
}
