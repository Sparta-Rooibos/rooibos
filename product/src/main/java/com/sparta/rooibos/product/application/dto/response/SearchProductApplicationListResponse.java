package com.sparta.rooibos.product.application.dto.response;

import java.util.UUID;

public record SearchProductApplicationListResponse(UUID id, String name, String clientId, String managedHubId) {
}
