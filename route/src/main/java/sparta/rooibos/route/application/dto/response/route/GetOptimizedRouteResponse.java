package sparta.rooibos.route.application.dto.response.route;

import sparta.rooibos.route.application.core.DijkstraAlgorithm;

import java.util.List;

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
            String fromHubName,
            String toHubName,
            Integer distance,
            Integer timeCost
    ) {
        public static RouteInfo of(String fromHubName, String toHubName, Integer distance, Integer timeCost) {
            return new RouteInfo(fromHubName, toHubName, distance, timeCost);
        }
    }
}
