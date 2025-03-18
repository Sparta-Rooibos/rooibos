package sparta.rooibos.route.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.rooibos.route.domain.model.Route;

import java.util.UUID;

public interface JpaRouteRepository extends JpaRepository<Route, UUID> {
}
