package com.sparta.rooibus.order.domain.repository;

import com.sparta.rooibus.order.application.dto.request.SearchOrderRequestDTO;
import com.sparta.rooibus.order.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// TODO : application코드 import 받는거 처리
public interface OrderQueryRepository {
    Page<Order> searchOrders(SearchOrderRequestDTO request, Pageable pageable);
}
