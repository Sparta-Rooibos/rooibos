package com.sparta.rooibos.client.presentation.dto.req;

import com.sparta.rooibos.client.application.dto.req.UpdateClientApplicationRequest;

import java.util.UUID;

public record UpdateClientRequest(String name, String address) {
    public UpdateClientApplicationRequest toApplication(UUID id) {
        return new UpdateClientApplicationRequest(id, name, address);
    }
}
