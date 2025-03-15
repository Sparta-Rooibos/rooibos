package com.sparta.rooibos.client.application.dto.res;

import com.sparta.rooibos.client.domain.entity.Client;

import java.util.UUID;

public record CreateClientApplicationResponse(UUID id, String name) {

    public CreateClientApplicationResponse(Client client) {
        this(client.getId(), client.getName());
    }
}
