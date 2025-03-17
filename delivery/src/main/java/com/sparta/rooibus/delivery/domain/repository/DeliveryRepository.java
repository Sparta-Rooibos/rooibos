package com.sparta.rooibus.delivery.domain.repository;

import com.sparta.rooibus.delivery.domain.entity.Delivery;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository {
    Delivery save(Delivery order);
}
