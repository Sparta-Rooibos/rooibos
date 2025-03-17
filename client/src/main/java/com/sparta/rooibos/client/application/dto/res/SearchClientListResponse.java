package com.sparta.rooibos.client.application.dto.res;

import java.util.UUID;

public record SearchClientListResponse(UUID id, String name, String type, String managedHubId, String address, boolean isDeleted) {}
