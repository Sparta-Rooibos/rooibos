package sparta.rooibos.hub.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sparta.rooibos.hub.domain.model.HubManager;
import sparta.rooibos.hub.domain.respository.HubManagerRepository;
import sparta.rooibos.hub.infrastructure.jpa.JpaHubManagerRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubManagerRepositoryImpl implements HubManagerRepository {

    private final JpaHubManagerRepository jpaHubManagerRepository;

    @Override
    public HubManager createHubManager(UUID userId, UUID hubId) {
        return null;
    }

    @Override
    public Optional<HubManager> getHubManagerByUserId(UUID userId) {
        return jpaHubManagerRepository.findHubManagerByUserIdAndDeletedAtIsNull(userId);
    }
}
