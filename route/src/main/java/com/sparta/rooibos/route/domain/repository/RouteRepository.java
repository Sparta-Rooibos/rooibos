package com.sparta.rooibos.route.domain.repository;

import com.sparta.rooibos.route.domain.model.Route;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RouteRepository {

    Route createRoute(Route route);

    Optional<Route> getRoute(UUID routeId);

    List<Route> getAllRoutes();

    List<Route> getAllRoutesByHubId(UUID hubId);

    List<Route> searchRoute(
            UUID fromHubId,
            UUID toHubId,
            String sort,
            int size,
            LocalDateTime lastCreatedAt,
            Integer lastDistance,
            Integer lastTimeCost
    );
}
