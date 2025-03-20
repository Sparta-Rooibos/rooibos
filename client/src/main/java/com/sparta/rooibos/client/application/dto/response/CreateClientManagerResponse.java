package com.sparta.rooibos.client.application.dto.response;

import com.sparta.rooibos.client.domain.entity.ClientManager;

import java.util.UUID;

public record CreateClientManagerResponse(
        UUID clientId,
        String userId
) {
    public static CreateClientManagerResponse create(ClientManager clientManager) {
        return new CreateClientManagerResponse(
                clientManager.getClientId(),
                clientManager.getUserId()
        );
    }
}
