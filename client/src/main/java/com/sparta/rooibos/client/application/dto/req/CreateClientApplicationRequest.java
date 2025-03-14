package com.sparta.rooibos.client.application.dto.req;

import com.sparta.rooibos.client.presentation.dto.req.CreateClientRequest;

public record CreateClientApplicationRequest(String name, String clientType, String managedHubId, String address) {
    public CreateClientApplicationRequest(CreateClientRequest createClientRequest) {
        this(createClientRequest.name(), createClientRequest.clientType(), createClientRequest.managedHubId(), createClientRequest.address());
    }
}