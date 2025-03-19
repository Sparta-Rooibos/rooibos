package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibus.order.application.dto.request.SearchOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponse;
import com.sparta.rooibus.order.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponse;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponseDTO;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.Pagination;
import com.sparta.rooibus.order.domain.repository.OrderQueryRepository;
import com.sparta.rooibus.order.domain.repository.OrderRepository;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MasterOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    public CreateOrderResponse createOrder(@Valid CreateOrderRequest request) {
        Order order = Order.create(
            request.requestClientId(),
            request.receiveClientId(),
            request.productId(),
            request.quantity(),
            request.requirement()
            );

        orderRepository.save(order);

//      TODO : feign client로 배송 ID 받아와서 order에 넣기 지금은 랜덤으로 넣음.
        CreateDeliveryResponse deliveryFeignResult = deliveryService.createDelivery(CreateDeliveryRequest.from(order),"Role_Master");
        UUID deliveryId = deliveryFeignResult.deliveryId();
        UUID departureId = deliveryFeignResult.departure();
        order.setDeliveryInfo(deliveryId,departureId);
        return CreateOrderResponse.from(order);
    }

    @Transactional
    @CachePut(value = "orderCache", key = "#request.id()")
    public UpdateOrderResponse updateOrder(UpdateOrderRequest request) {
        Order targetOrder = orderRepository.findById(request.id()).orElseThrow(
            ()-> new IllegalArgumentException("수정할 주문을 찾을 수 없습니다.")
        );

        targetOrder.update(
            request.requestClientId(),
            request.receiveClientId(),
            request.productId(),
            request.quantity(),
            request.requirement()
        );

        return UpdateOrderResponse.from(targetOrder);
    }

    @Transactional
    @CacheEvict(value = "orderCache", key = "#orderId")
    public DeleteOrderResponse deleteOrder(UUID orderId) {
        Order targetOrder = orderRepository.findById(orderId).orElseThrow(
            ()-> new IllegalArgumentException("삭제할 주문이 없습니다.")
        );

        targetOrder.delete();

        return DeleteOrderResponse.from(targetOrder);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "orderCache", key = "#orderId")
    public GetOrderResponse getOrder(UUID orderId) {
        Order targetOrder = orderRepository.findById(orderId).orElseThrow(
            ()-> new IllegalArgumentException("해당 주문이 없습니다.")
        );

        return GetOrderResponse.from(targetOrder);
    }

    @Cacheable(value = "searchOrderCache", key = "#request.page() + '-' + #request.size()")
    public Pagination<SearchOrderResponseDTO> searchOrders(SearchOrderRequestDTO request) {
        PageRequest pageRequest = PageRequest.of(request.page(), request.size());
        Page<Order> ordersPage = orderQueryRepository.searchOrders(request, pageRequest);

        List<SearchOrderResponseDTO> orderResponseDTOs = ordersPage.getContent()
            .stream()
            .map(SearchOrderResponseDTO::new)
            .collect(Collectors.toList());

//        return new Pagination<>(orderResponseDTOs, pageRequest, ordersPage.getTotalElements());
        return null;
    }
}
