package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.critreia.ClientCriteria;
import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.model.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryClientRepository {
    Pagination<Client> getClientList(ClientCriteria criteria);
}
