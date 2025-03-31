package com.sparta.rooibos.stock.application.dto.request;

import java.util.UUID;

public record UpdateMultiStockListRequest(
        UUID id,
        int productQuantity
) {
    public static UpdateMultiStockListRequest of(UUID uuid, int i) {
        return new UpdateMultiStockListRequest(uuid, i);
    }
}
