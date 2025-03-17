package sparta.rooibos.hub.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sparta.rooibos.hub.domain.model.Hub;

import java.util.Optional;
import java.util.UUID;

public interface JpaHubRepository extends JpaRepository<Hub, UUID> {
    @Query(
            value = "SELECT h FROM Hub h " +
                    "WHERE h.hubId = :hubId " +
                    "AND h.deleteAt IS NULL"
    )
    Optional<Hub> getActiveHub(@Param("hubId") UUID hubId);
}
