package com.sparta.rooibos.product.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(@NotNull String name, @NotNull String clientId) {
}
