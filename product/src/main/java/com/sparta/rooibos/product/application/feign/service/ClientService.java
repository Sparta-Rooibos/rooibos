package com.sparta.rooibos.product.application.feign.service;

import com.sparta.rooibos.product.domain.feign.dto.Client;

import java.util.Optional;

public interface ClientService {
    Optional<Client> getClient(String email,String name,String role, String clientId);

    String getClientId(String email);
}
