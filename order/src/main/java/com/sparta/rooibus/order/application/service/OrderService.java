package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.request.SearchOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.request.DeliveryRequestDTO;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponseDTO;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.repository.OrderQueryRepository;
import com.sparta.rooibus.order.domain.repository.OrderRepository;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponseDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    public CreateOrderResponseDTO createOrder(@Valid CreateOrderRequestDTO request) {

        Order order = new Order(request);   // 주문 객체 생성
        orderRepository.save(order);        // DB에 저장

        DeliveryRequestDTO deliveryRequest = new DeliveryRequestDTO(order);
        CreateOrderResponseDTO response = deliveryService.createDelivery(deliveryRequest);
        return response;
    }

    @Transactional
    @CachePut(value = "orderCache", key = "#request.id()")
    public UpdateOrderResponseDTO updateOrder(UpdateOrderRequestDTO request) {
        Order targetOrder = orderRepository.findById(request.id()).orElseThrow(
            ()-> new IllegalArgumentException("수정할 주문이 없습니다.")
        );

        targetOrder.update(request);

        UpdateOrderResponseDTO response = new UpdateOrderResponseDTO(targetOrder);
        return response;
    }

    @Transactional
    @CacheEvict(value = "orderCache", key = "#orderId")
    public DeleteOrderResponseDTO deleteOrder(UUID orderId) {

        Order targetOrder = orderRepository.findById(orderId).orElseThrow(
            ()-> new IllegalArgumentException("삭제할 주문이 없습니다.")
        );


        targetOrder.delete();

        DeleteOrderResponseDTO response = new DeleteOrderResponseDTO(targetOrder);
        return response;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "orderCache", key = "#orderId")
    public GetOrderResponseDTO getOrder(UUID orderId) {

        Order targetOrder = orderRepository.findById(orderId).orElseThrow(
            ()-> new IllegalArgumentException("해당 주문이 없습니다.")
        );

        GetOrderResponseDTO response = new GetOrderResponseDTO(targetOrder);
        return response;
    }

    @Cacheable(value = "searchOrderCache", key = "#request.page() + '-' + #request.size()")
    public Page<SearchOrderResponseDTO> searchOrders(SearchOrderRequestDTO request) {
        PageRequest pageRequest = PageRequest.of(request.page(), request.size());
        Page<Order> ordersPage = orderQueryRepository.searchOrders(request, pageRequest);

        List<SearchOrderResponseDTO> orderResponseDTOs = ordersPage.getContent()
            .stream()
            .map(SearchOrderResponseDTO::new)
            .collect(Collectors.toList());

        return new PageImpl<>(orderResponseDTOs, pageRequest, ordersPage.getTotalElements());

    }
}
