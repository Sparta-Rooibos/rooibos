package sparta.rooibos.hub.domain.respository;

import sparta.rooibos.hub.domain.model.Hub;

import java.util.Optional;
import java.util.UUID;

public interface HubRepository {
    Hub createHub(Hub hub);

    Optional<Hub> getHub(UUID hubId);
}
