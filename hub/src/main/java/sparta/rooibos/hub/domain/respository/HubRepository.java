package sparta.rooibos.hub.domain.respository;

import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.model.Pagination;

import java.util.Optional;
import java.util.UUID;

public interface HubRepository {
    Hub createHub(Hub hub);

    Optional<Hub> getHub(UUID hubId);

    Optional<Hub> getHubByRegion(String region);

    Pagination<Hub> searchHub(String name, String region, int page, int size);
}
