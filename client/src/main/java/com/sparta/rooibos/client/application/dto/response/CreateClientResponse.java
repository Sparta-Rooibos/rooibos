package com.sparta.rooibos.client.application.dto.response;

import com.sparta.rooibos.client.domain.entity.Client;

import java.util.UUID;

public record CreateClientResponse(UUID id, String name) {

    public CreateClientResponse(Client client) {
        this(client.getId(), client.getName());
    }
}
