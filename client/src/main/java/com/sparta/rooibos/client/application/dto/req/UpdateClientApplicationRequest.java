package com.sparta.rooibos.client.application.dto.req;

import com.sparta.rooibos.client.presentation.dto.req.UpdateClientRequest;

import java.util.UUID;

public record UpdateClientApplicationRequest(UUID clientId, String name, String address) {

    public UpdateClientApplicationRequest(UUID clientId, UpdateClientRequest request) {
        this(clientId,request.name(),request.address());
    }
}
