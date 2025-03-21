package com.sparta.rooibos.client.application.dto.response;

import java.util.UUID;

public record SearchClientListResponse(UUID id, String name, String type, String managedHubId, String address, boolean isDeleted) {}
