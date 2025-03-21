package com.sparta.rooibos.client.application.dto.request;

import com.sparta.rooibos.client.domain.critreia.ClientCriteria;

public record SearchClientRequest(String name, String address, String type, Boolean isDeleted, Integer page,
                                  Integer size, String sort) {
    public SearchClientRequest {
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        sort = sort == null ? "createdAt" : sort;
    }

    public ClientCriteria toCriteria() {
        return new ClientCriteria(
                name, address, type, isDeleted, page, size, sort

        );
    }
}
