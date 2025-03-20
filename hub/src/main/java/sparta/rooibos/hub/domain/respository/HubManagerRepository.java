package sparta.rooibos.hub.domain.respository;

import sparta.rooibos.hub.domain.model.HubManager;

import java.util.Optional;
import java.util.UUID;

public interface HubManagerRepository {
    HubManager createHubManager(UUID userId, UUID hubId);

    Optional<HubManager> getHubManagerByUserId(UUID userId);
}
