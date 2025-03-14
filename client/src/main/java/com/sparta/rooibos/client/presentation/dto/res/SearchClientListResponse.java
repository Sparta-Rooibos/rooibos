package com.sparta.rooibos.client.presentation.dto.res;

import com.sparta.rooibos.client.application.service.dto.res.SearchClientApplicationListResponse;

import java.util.UUID;

public record SearchClientListResponse(UUID id, String name, String type, String managedHubId, String address) {
    public SearchClientListResponse(SearchClientApplicationListResponse response) {
        this(response.id(), response.name(), response.type(), response.managedHubId(), response.address());
    }

}
