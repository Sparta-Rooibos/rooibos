package com.sparta.rooibos.deliverer.domain.repository;

import com.sparta.rooibos.deliverer.domain.critia.DelivererSearchCriteria;
import com.sparta.rooibos.deliverer.domain.entity.Deliverer;
import com.sparta.rooibos.deliverer.domain.model.Pagination;

public interface DelivererRepositoryCustom {
    Pagination<Deliverer> searchDeliverers(DelivererSearchCriteria request);
}
