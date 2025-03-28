package com.sparta.rooibos.route.application.dto.response.route;

import com.sparta.rooibos.route.domain.model.Route;

import java.util.UUID;

public record GetRouteResponse(
        UUID fromHubId,
        UUID toHubId,
        Integer distance,
        Integer timeCost
) {
    public static GetRouteResponse from(Route route) {
        return new GetRouteResponse(
                route.getFromHubId(),
                route.getToHubId(),
                route.getDistance(),
                route.getTimeCost()
        );
    }
}
