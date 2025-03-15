package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(UUID id);
    Page<Client> findAll(Pageable pageable);
}
