package sparta.rooibos.hub.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sparta.rooibos.hub.domain.model.Hub;
import sparta.rooibos.hub.domain.respository.HubRepository;
import sparta.rooibos.hub.infrastructure.jpa.JpaHubRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepository {

    private final JpaHubRepository jpaRepository;

    @Override
    public Hub createHub(Hub hub) {
        return jpaRepository.save(hub);
    }

    @Override
    public Optional<Hub> getHub(UUID hubId) {
        return jpaRepository.getActiveHub(hubId);
    }
}
