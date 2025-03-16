package com.sparta.rooibos.product.application.dto.request;

import java.util.UUID;

public record UpdateProductApplicationRequest(UUID productId, String name) {
}
