package com.sparta.rooibos.order.application.dto.request;

public record SearchRequest(
    String keyword,
    String filterKey,
    String filterValue,
    String sort,
    int page,
    int size
) {

}
