package com.sparta.rooibos.route.application.dto.response.route;

import com.sparta.rooibos.route.application.core.DijkstraAlgorithm;

import java.util.List;
import java.util.UUID;

public record GetOptimizedRouteResponse(
        DijkstraAlgorithm.Result result,
        Integer totalDistance,
        Integer totalTime,
        List<RouteInfo> routeInfos
) {
    public static GetOptimizedRouteResponse from(DijkstraAlgorithm.Result result, List<RouteInfo> routeInfos) {
        Integer totalDistance = routeInfos.stream().mapToInt(RouteInfo::distance).sum();
        Integer totalTime = routeInfos.stream().mapToInt(RouteInfo::timeCost).sum();

        return new GetOptimizedRouteResponse(result, totalDistance, totalTime, routeInfos);
    }

    public record RouteInfo(
            UUID fromHubId,
            String fromHubName,
            UUID toHubId,
            String toHubName,
            Integer distance,
            Integer timeCost
    ) {
        public static RouteInfo of(
                UUID fromHubId, String fromHubName, UUID toHubId, String toHubName, Integer distance, Integer timeCost
        ) {
            return new RouteInfo(fromHubId, fromHubName, toHubId, toHubName, distance, timeCost);
        }
    }
}
