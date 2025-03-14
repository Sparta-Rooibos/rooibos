package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ClientRepository {
    Client save(Client client);
    List<Client> findAll();

    Optional<Client> findById(UUID id);
}
