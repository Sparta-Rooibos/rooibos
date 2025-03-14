package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.Client;

import java.util.List;


public interface ClientRepository {
    Client save(Client client);
    List<Client> findAll();
}
