package com.sparta.rooibos.client.presentation.dto.res;

import com.sparta.rooibos.client.application.dto.res.SearchClientApplicationListResponse;

import java.util.UUID;

public record SearchClientListResponse(UUID id, String name, String type, String managedHubId, String address, boolean isDeleted) {
    public SearchClientListResponse(SearchClientApplicationListResponse response) {
        this(response.id(), response.name(), response.type(), response.managedHubId(), response.address(), response.isDeleted());
    }

}
