package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.ClientManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientManagerRepository {
    ClientManager save(ClientManager clientManager);
    List<ClientManager> findAllByClientId(UUID clientId);
    void deleteByClientId(UUID clientId);

    String getClientIdByUserId(String email);
}
