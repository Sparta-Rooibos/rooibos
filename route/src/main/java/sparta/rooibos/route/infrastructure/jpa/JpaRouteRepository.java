package sparta.rooibos.route.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.rooibos.route.domain.model.Route;

import java.util.Optional;
import java.util.UUID;

public interface JpaRouteRepository extends JpaRepository<Route, UUID> {
    @Query(
            value = "SELECT r FROM Route r " +
                    "WHERE r.routeId = :routeId " +
                    "AND r.deleteAt IS NULL"
    )
    Optional<Route> getRoute(UUID routeId);
}
