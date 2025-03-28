package com.sparta.rooibos.order.domain.repository;

import com.sparta.rooibos.order.domain.entity.Order;
import com.sparta.rooibos.order.domain.model.Pagination;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Optional<Order> findByUserId(UUID orderId, UUID userId);
    Optional<Order> findByIdAndHub(UUID id,UUID hubId);

    Pagination<Order> searchOrdersByHubId(String keyword, String filterKey, String filterValue, String sort, int page, int size, UUID hubId);
    Pagination<Order> searchOrdersByUserId(String keyword, String filterKey, String filterValue, String sort, int page, int size, UUID userId);
    Pagination<Order> searchOrders(String keyword, String filterKey, String filterValue, String sort, int page, int size);
}
