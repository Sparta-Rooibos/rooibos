package com.sparta.rooibos.deliverer.domain.critia;

public record DelivererSearchCriteria(
        String keyword,
        String sort,
        String filter,
        int page,
        int size
) {
}
