package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.Client;

import java.util.Optional;
import java.util.UUID;


public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findByIdAndDeleteByIsNull(UUID id);
    Optional<Client> findByNameAndDeleteByIsNull(String name);
}
