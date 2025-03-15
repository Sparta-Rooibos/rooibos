package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.dto.request.DeliveryRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryService {
    // 주문 생성
    CreateOrderResponseDTO createDelivery(DeliveryRequestDTO request);
}
