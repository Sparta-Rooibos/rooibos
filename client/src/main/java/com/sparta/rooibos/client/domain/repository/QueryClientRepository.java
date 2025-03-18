package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryClientRepository {
    Page<Client> getClientList(Pageable pageable, String name, String address, String type, Boolean deleteCheck);
}
