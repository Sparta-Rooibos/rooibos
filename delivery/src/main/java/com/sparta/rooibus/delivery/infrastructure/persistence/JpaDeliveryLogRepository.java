package com.sparta.rooibus.delivery.infrastructure.persistence;

import com.sparta.rooibus.delivery.domain.entity.DeliveryLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDeliveryLogRepository extends JpaRepository<DeliveryLog, UUID> {

}
