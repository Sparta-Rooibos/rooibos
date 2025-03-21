package com.sparta.rooibos.client.application.dto.request;

import com.sparta.rooibos.client.domain.entity.ClientManager;

import java.util.UUID;

public record CreateClientManagerRequest(
        UUID clientId,
        String userId
) {
    public ClientManager toEntity() {
        return ClientManager.create(
                clientId,
                userId
        );
    }
}
