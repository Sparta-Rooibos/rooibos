package sparta.rooibos.hub.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.rooibos.hub.domain.model.Hub;

import java.util.UUID;

public interface JpaHubRepository extends JpaRepository<Hub, UUID> {
}
