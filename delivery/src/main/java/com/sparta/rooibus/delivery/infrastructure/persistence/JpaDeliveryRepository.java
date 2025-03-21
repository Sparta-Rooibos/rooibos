package com.sparta.rooibus.delivery.infrastructure.persistence;

import com.sparta.rooibus.delivery.domain.entity.Delivery;
import com.sparta.rooibus.delivery.domain.repository.DeliveryRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID>, DeliveryRepository {

}
