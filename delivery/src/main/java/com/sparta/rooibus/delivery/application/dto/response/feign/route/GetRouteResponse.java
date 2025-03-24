package com.sparta.rooibus.delivery.application.dto.response.feign.route;

import java.util.List;
import java.util.UUID;

public record GetRouteResponse(
    List<RouteResponse> routeList
    ) {
    public record RouteResponse(
        UUID fromHubId,
        UUID toHubId,
        Integer distance,
        Integer timeCost
    ) {

    }
}
