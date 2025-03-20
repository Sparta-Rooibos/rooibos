package com.sparta.rooibus.order.domain.repository;

import com.sparta.rooibus.order.application.dto.request.SearchRequest;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.Pagination;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Pagination<Order> searchOrders(SearchRequest searchRequest);

    Optional<Order> findByIdAndHub(UUID id,UUID hubId);

    Pagination<Order> searchOrdersByHubId(SearchRequest searchRequest, UUID hubId);

    Optional<Order> findByUserId(UUID orderId, UUID userId);

    Pagination<Order> searchOrdersByUserId(SearchRequest searchRequest, UUID userId);
}
