package com.sparta.rooibos.product.application.dto.request;

import java.util.UUID;

public record UpdateProductRequest(UUID productId, String name) {
}
