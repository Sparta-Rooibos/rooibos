package com.sparta.rooibos.delivery.application.dto.request.feign.deliverAgent;

import java.util.UUID;

public record GetHubDeliverRequest(UUID departure) {
    public static GetHubDeliverRequest from(UUID departure) {
        return new GetHubDeliverRequest(departure);
    }
}
