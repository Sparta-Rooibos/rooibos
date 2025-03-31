package com.sparta.rooibos.deliverer.application.dto.request;

import com.sparta.rooibos.deliverer.domain.critia.DelivererSearchCriteria;

public record DelivererSearchRequest(
     String keyword,
     String sort,
     String filter,
     int page,
     int size
){
    public DelivererSearchCriteria toCriteria() {
        return new DelivererSearchCriteria(keyword, sort, filter, page, size);
    }
}