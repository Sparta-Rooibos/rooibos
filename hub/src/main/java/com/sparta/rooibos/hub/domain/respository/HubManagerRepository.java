package com.sparta.rooibos.hub.domain.respository;

import com.sparta.rooibos.hub.domain.model.HubManager;

import java.util.Optional;
import java.util.UUID;

public interface HubManagerRepository {
    HubManager createHubManager(UUID userId, UUID hubId);

    Optional<UUID> getHubIdByUserId(UUID userId);

    Optional<UUID> getHubIdByEmail(String email);

    Optional<HubManager> getHubManagerByUserId(UUID userId);
}
