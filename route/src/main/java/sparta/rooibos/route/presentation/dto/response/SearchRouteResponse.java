package sparta.rooibos.route.presentation.dto.response;

import sparta.rooibos.route.domain.model.Route;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record SearchRouteResponse(
        List<RouteResponse> routes,
        PaginationResult result

) {
    public static SearchRouteResponse from(List<Route> routes, String sort) {
        if (routes.isEmpty()) {
            return new SearchRouteResponse(
                    new ArrayList<>(),
                    new PaginationResult(sort, null, null, null)
            );
        }

        return new SearchRouteResponse(
                routes.stream().map(RouteResponse::from).toList(),
                PaginationResult.of(sort, routes.get(routes.size() - 1))
        );
    }

    public record PaginationResult(
            String sort,
            LocalDateTime lastCreatedAt,
            String lastDistance,
            String lastTimeCost
    ) {
        public static PaginationResult of(String sort, Route route) {
            return new PaginationResult(
                    sort,
                    route.getCreateAt(),
                    route.getDistance(),
                    route.getTimeCost()
            );
        }
    }

    public record RouteResponse(
            UUID fromHubId,
            UUID toHubId,
            String distance,
            String timeCost
    ) {
        public static RouteResponse from(Route route) {
            return new RouteResponse(
                    route.getFromHubId(),
                    route.getToHubId(),
                    route.getDistance(),
                    route.getTimeCost()
            );
        }
    }
}
