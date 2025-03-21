package com.sparta.rooibus.delivery.infrastructure.persistence;

import com.sparta.rooibus.delivery.domain.entity.Delivery;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> {
    Optional<Delivery> findByClientDeliverAndIdAndDeletedAtIsNull(UUID clientDeliverId,UUID deliveryId);
    Optional<Delivery> findByDepartureAndIdAndDeletedAtIsNull(UUID deliveryId,UUID departureId);
}
