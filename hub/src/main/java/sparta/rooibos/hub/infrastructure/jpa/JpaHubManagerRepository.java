package sparta.rooibos.hub.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.rooibos.hub.domain.model.HubManager;

import java.util.Optional;
import java.util.UUID;

public interface JpaHubManagerRepository extends JpaRepository<HubManager, UUID> {

    Optional<HubManager> findHubManagerByUserIdAndDeletedAtIsNull(UUID userId);
}
