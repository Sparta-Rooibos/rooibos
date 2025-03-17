package sparta.rooibos.hub.domain.respository;

import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.model.Pagination;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository {
    Hub createHub(Hub hub);

    Optional<Hub> getHub(UUID hubId);

    Pagination<Hub> searchHub(String name, String region, int page, int size);
}
