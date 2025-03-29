package com.sparta.rooibos.delivery.application.dto.request;

public record SearchDeliveryRequestDTO(
    String keyword,
    String filterKey,
    String filterValue,
    String sort,
    int page,
    int size
) {

}
