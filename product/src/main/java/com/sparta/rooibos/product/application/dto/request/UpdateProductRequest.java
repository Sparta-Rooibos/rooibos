package com.sparta.rooibos.product.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateProductRequest(@NotNull String name) {
}
