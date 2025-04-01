package com.sparta.rooibos.deliverer.domain.dto.critria;

public record DelivererSearchCriteria(
        String keyword,
        String sort,
        String filter,
        int page,
        int size
) {
}
