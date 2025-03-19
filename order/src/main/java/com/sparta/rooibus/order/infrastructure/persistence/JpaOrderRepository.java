package com.sparta.rooibus.order.infrastructure.persistence;

import com.sparta.rooibus.order.domain.entity.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, UUID>{

}
