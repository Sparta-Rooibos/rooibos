package com.sparta.rooibos.deliverer.domain.repository;

import com.sparta.rooibos.deliverer.application.dto.request.DelivererSearchRequest;
import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import com.sparta.rooibos.deliverer.domain.model.Pagination;

public interface DelivererRepositoryCustom {
    Pagination<Deliverer> searchDeliverers(DelivererSearchRequest request);
}
