package com.sparta.rooibus.delivery.application.dto.request.feign.deliverAgent;

import java.util.UUID;

public record GetDeliverRequest(UUID clientId) {

    public static GetDeliverRequest from(UUID clientId) {
        return new GetDeliverRequest(clientId);
    }
}
