package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.dto.request.DeliveryRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryService {

    CreateOrderResponseDTO createDelivery(DeliveryRequestDTO request);
}
