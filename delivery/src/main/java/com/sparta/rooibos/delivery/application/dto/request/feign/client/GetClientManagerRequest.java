package com.sparta.rooibos.delivery.application.dto.request.feign.client;

import java.util.UUID;

public record GetClientManagerRequest(UUID clientId) {

    public static GetClientManagerRequest from(UUID clientId) {
        return new GetClientManagerRequest(clientId);
    }
}
