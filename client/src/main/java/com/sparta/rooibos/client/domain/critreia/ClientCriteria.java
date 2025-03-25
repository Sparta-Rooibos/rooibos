package com.sparta.rooibos.client.domain.critreia;

public record ClientCriteria(String name, String address, String type, Boolean isDeleted, Integer page,
                             Integer size, String sort) {
}
