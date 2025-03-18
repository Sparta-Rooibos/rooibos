package sparta.rooibos.route.domain.repository;

import sparta.rooibos.route.domain.model.Route;

import java.util.Optional;
import java.util.UUID;

public interface RouteRepository {

    Route createRoute(Route route);

    Optional<Route> getRoute(UUID routeId);
}
