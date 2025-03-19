package com.sparta.rooibus.order.domain.repository;

import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.Pagination;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Pagination<Order> searchOrders(String keyword, String filterKey, String filterValue, String sort, int page, int size);
}
