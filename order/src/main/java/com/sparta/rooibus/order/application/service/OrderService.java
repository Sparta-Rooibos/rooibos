package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequestDTO;
import com.sparta.rooibus.order.application.port.FeignDeliveryClient;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.repository.OrderRepository;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
//    private final DeliveryClient deliveryClient;  TODO : delivery 완료후 체크

    public OrderService(OrderRepository orderRepository, FeignDeliveryClient deliveryClient) {
        this.orderRepository = orderRepository;
//        this.deliveryClient = deliveryClient;     TODO : delivery 완료후 체크
    }

    @Transactional
    public UUID createOrder(@Valid CreateOrderRequestDTO request) {
//      1. 주문 생성
        Order order = new Order(request);   // 주문 객체 생성
        orderRepository.save(order);        // DB에 저장

//        2. 배달 서비스 호출                          TODO : delivery 완료후 체크
//        CreateDeliveryRequestDTO deliveryRequestDTO = new CreateDeliveryRequestDTO(order);
//        deliveryClient.createDelivery(deliveryRequestDTO);

        return order.getId();
    }

}
