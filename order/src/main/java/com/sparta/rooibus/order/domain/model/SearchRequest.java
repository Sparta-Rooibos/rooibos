package com.sparta.rooibus.order.domain.model;

public record SearchRequest(
    String keyword,
    String filterKey,
    String filterValue,
    String sort,
    int page,
    int size
) {

}
