package com.sparta.rooibus.order.application.dto.request;

public record SearchOrderRequestDTO(
    String keyword,
    String filterKey,
    String filterValue,
    String sort,
    int page,
    int size
) {}
