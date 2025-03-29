package com.sparta.rooibos.delivery.application.dto.request.feign.deliverAgent;

import java.util.UUID;

public record GetDeliverRequest(
    String type,
    UUID hubId
) {

    public static GetDeliverRequest of(String type,UUID hubId) {
        return new GetDeliverRequest(type,hubId);
    }
}
