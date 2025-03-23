package com.sparta.rooibos.client.domain.repository;

import com.sparta.rooibos.client.domain.entity.Client;
import com.sparta.rooibos.client.domain.model.Pagination;

public interface QueryClientRepository {
    Pagination<Client> searchClient(ClientCriteria criteria);
}
