package com.sparta.rooibos.client.presentation.dto.req;

import com.sparta.rooibos.client.application.dto.req.UpdateHubIdApplicationRequest;

import java.util.UUID;

public record UpdateHubIdRequest(String hubId) {
    public UpdateHubIdApplicationRequest toApplication(UUID clientId) {
        return new UpdateHubIdApplicationRequest(clientId, hubId);
    }
}
