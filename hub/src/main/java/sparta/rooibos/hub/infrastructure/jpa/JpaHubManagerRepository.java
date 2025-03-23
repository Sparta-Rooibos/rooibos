package sparta.rooibos.hub.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.rooibos.hub.domain.model.HubManager;

import java.util.Optional;
import java.util.UUID;

public interface JpaHubManagerRepository extends JpaRepository<HubManager, UUID> {

    @Query(
            value = "SELECT hm.belongingHubId FROM HubManager hm " +
                    "WHERE hm.userId = :userId " +
                    "AND hm.deletedAt IS NULL"
    )
    Optional<UUID> getHubIdByUserID(UUID userId);

    @Query(
            value = "SELECT hm.belongingHubId FROM HubManager hm " +
                    "WHERE hm.email = :email " +
                    "AND hm.deletedAt IS NULL"
    )
    Optional<UUID> getHubIdByEmail(String email);

    Optional<HubManager> getHubManagerByUserId(UUID userId);
}
