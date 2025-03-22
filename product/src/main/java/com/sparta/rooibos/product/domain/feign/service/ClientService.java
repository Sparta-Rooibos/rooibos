package com.sparta.rooibos.product.domain.feign.service;

import com.sparta.rooibos.product.domain.feign.dto.Client;

public interface ClientService {
    Client getClient(String clientId);
    String getClientId(String email);
}
