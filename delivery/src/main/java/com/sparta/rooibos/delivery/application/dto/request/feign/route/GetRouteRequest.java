package com.sparta.rooibos.delivery.application.dto.request.feign.route;

import java.util.UUID;

public record GetRouteRequest(
    UUID fromHubId,
    UUID toHubId,
    String priorityType
) {

    public static GetRouteRequest of(UUID fromHubId, UUID toHubId) {
        return new GetRouteRequest(fromHubId, toHubId,"DISTANCE");
    }
}
