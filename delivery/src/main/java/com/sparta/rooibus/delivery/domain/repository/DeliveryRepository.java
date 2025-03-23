package com.sparta.rooibus.delivery.domain.repository;

import com.sparta.rooibus.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository {
    Delivery save(Delivery order);

    Optional<Delivery> findById(UUID deliveryId);
}
