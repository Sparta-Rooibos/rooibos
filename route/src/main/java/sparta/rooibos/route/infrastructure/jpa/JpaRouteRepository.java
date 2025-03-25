package sparta.rooibos.route.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sparta.rooibos.route.domain.model.Route;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaRouteRepository extends JpaRepository<Route, UUID> {
    @Query(
            value = "SELECT r FROM Route r " +
                    "WHERE r.routeId = :routeId " +
                    "AND r.deletedAt IS NULL"
    )
    Optional<Route> getRoute(@Param("routeId") UUID routeId);

    @Query(
            value = "SELECT r FROM Route r " +
                    "WHERE r.deletedAt IS NULL"
    )
    List<Route> getAllRoutes();

    @Query(
            value = "SELECT r FROM Route r " +
                    "WHERE r.fromHubId = :hubId " +
                    "OR r.toHubId = :hubId"
    )
    List<Route> getAllRoutesByHubId(@Param("hubId") UUID hubId);
}
