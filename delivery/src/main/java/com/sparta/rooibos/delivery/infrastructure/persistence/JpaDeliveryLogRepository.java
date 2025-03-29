package com.sparta.rooibos.delivery.infrastructure.persistence;

import com.sparta.rooibos.delivery.domain.entity.DeliveryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaDeliveryLogRepository extends JpaRepository<DeliveryLog, UUID> {

    List<DeliveryLog> findAllByDeliveryIdAndDeletedAtIsNull(UUID deliveryId);
}
