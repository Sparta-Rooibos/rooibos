package com.sparta.rooibos.product.presentation.dto.response;

import java.util.UUID;

public record SearchProductListResponseDto(UUID id, String name, String clientId, String managedHubId, boolean isDeleted) {
}
