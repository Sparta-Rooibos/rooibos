package com.sparta.rooibos.delivery.infrastructure.persistence;

import com.sparta.rooibos.delivery.domain.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> {
    Optional<Delivery> findByClientDeliverAndIdAndDeletedAtIsNull(UUID clientDeliverId,UUID deliveryId);
    Optional<Delivery> findByDepartureAndIdAndDeletedAtIsNull(UUID departureId,UUID deliveryId);
}
