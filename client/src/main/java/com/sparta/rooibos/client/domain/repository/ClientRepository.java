package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.Client;


public interface ClientRepository {
    Client save(Client client);
}
