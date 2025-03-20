package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.ClientManager;

import java.util.Optional;
import java.util.UUID;

public interface ClientManagerRepository {
    ClientManager save(ClientManager clientManager);
    Optional<ClientManager> findById(UUID clientId);
    void deleteByClientId(UUID clientId);
}
