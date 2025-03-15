package com.sparta.rooibos.client.presentation.dto.req;

import com.sparta.rooibos.client.application.dto.req.CreateClientApplicationRequest;

public record CreateClientRequest(String name, String clientType, String managedHubId, String address) {
    public CreateClientApplicationRequest toApplication() {
        return new CreateClientApplicationRequest(name, clientType, managedHubId, address);
    }
}