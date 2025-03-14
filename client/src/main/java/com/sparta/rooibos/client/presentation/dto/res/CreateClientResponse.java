package com.sparta.rooibos.client.presentation.dto.res;

import com.sparta.rooibos.client.application.service.dto.res.CreateClientApplicationResponse;

import java.util.UUID;

public record CreateClientResponse(UUID id, String name) {

    public CreateClientResponse(CreateClientApplicationResponse client) {
        this(client.id(),client.name());
    }
}
