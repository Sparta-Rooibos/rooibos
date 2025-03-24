package com.sparta.rooibus.delivery.application.dto.response.feign.route;

import java.util.List;
import java.util.UUID;

public record GetRouteResponse(
    Result result,
    int totalDistance,
    int totalTime,
    List<RouteInfo> routeInfos
) {
    public record Result(
        String priorityType,
        List<UUID> path
    ) {}

    public record RouteInfo(
        UUID fromHubId,
        UUID toHubId,
        String fromHubName,
        String toHubName,
        int distance,
        int timeCost
    ) {}
}
