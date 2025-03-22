package com.sparta.rooibos.stock.application.dto.request;

import jakarta.validation.constraints.PositiveOrZero;

public record UpdateStockRequest(@PositiveOrZero int quantity) {
}
