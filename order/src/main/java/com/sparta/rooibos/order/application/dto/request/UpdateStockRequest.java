package com.sparta.rooibos.order.application.dto.request;

public record UpdateStockRequest(int quantity) {

    public static UpdateStockRequest from(int i) {
        return new UpdateStockRequest(i);
    }
}
