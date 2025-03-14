package com.sparta.rooibos.client.application.service.dto.res;

import java.util.UUID;

public record SearchClientApplicationListResponse(UUID id, String name, String type, String managedHubId, String address) {
}
