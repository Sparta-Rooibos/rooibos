package com.sparta.rooibos.order.infrastructure.persistence;

import com.sparta.rooibos.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, UUID>{
    Optional<Order> findOrderByIdAndDeletedAtIsNull(UUID orderId);
    Optional<Order> findOrderByIdAndManageHubIDAndDeletedAtIsNull(UUID userId,UUID hubId);
    Optional<Order> findOrderByIdAndCreatedByAndDeletedAtIsNull(UUID userId, String orderId);
}
