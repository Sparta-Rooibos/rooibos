package sparta.rooibos.route.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sparta.rooibos.route.domain.model.Route;
import sparta.rooibos.route.domain.repository.RouteRepository;
import sparta.rooibos.route.infrastructure.jpa.JpaRouteRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {

    private final JpaRouteRepository jpaRouteRepository;

    @Override
    public Route createRoute(Route route) {
        return jpaRouteRepository.save(route);
    }

    @Override
    public Optional<Route> getRoute(UUID routeId) {
        return jpaRouteRepository.getRoute(routeId);
    }
}
