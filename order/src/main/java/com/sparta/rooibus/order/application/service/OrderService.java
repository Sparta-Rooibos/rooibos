package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.request.DeliveryRequestDTO;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponseDTO;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.repository.OrderRepository;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;

    @Transactional
    public CreateOrderResponseDTO createOrder(@Valid CreateOrderRequestDTO request) {
//      1. 주문 생성
        Order order = new Order(request);   // 주문 객체 생성
        orderRepository.save(order);        // DB에 저장

//      2. 배달 서비스 호출
        DeliveryRequestDTO deliveryRequest = new DeliveryRequestDTO(order);
        CreateOrderResponseDTO response = deliveryService.createDelivery(deliveryRequest);
        return response;
    }

    @Transactional
    public UpdateOrderResponseDTO updateOrder(UpdateOrderRequestDTO request) {
//      1. 목표 주문 찾기
        Order targetOrder = orderRepository.findById(request.id()).orElseThrow(
            ()-> new IllegalArgumentException("수정할 주문이 없습니다.")
        );

//      2. 주문 수정
        targetOrder.update(request);

        UpdateOrderResponseDTO response = new UpdateOrderResponseDTO(targetOrder);
        return response;
    }
}
