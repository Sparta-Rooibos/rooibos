package com.sparta.rooibus.order.infrastructure.persistence;

import com.sparta.rooibus.order.domain.entity.Order;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, UUID>{
    Optional<Order> findOrderByIdAndDeletedAtIsNull(UUID orderId);
    Optional<Order> findOrderByIdAndManageHubIDAndDeletedAtIsNull(UUID userId,UUID hubId);
    Optional<Order> findOrderByIdAndAndCreatedByAndDeletedAtIsNull(UUID userId, UUID orderId);
}
