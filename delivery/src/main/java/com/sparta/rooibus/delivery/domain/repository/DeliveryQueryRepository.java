package com.sparta.rooibus.delivery.domain.repository;

import com.sparta.rooibus.delivery.application.dto.request.SearchDeliveryRequestDTO;
import com.sparta.rooibus.delivery.domain.entity.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryQueryRepository {
    Page<Delivery> searchOrders(SearchDeliveryRequestDTO request, Pageable pageable);
}
