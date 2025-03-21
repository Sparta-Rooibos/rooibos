package com.sparta.rooibus.delivery.application.dto.request.feign.client;

import java.util.UUID;

public record GetClientRequest(
    UUID clientId
) {

    public static GetClientRequest from(UUID uuid) {
        return new GetClientRequest(uuid);
    }
}
