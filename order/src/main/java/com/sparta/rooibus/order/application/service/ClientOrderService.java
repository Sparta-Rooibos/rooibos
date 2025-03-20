package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.aop.UserContextRequestBean;
import com.sparta.rooibus.order.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibus.order.application.dto.request.SearchRequest;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibus.order.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponse;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponse;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponse;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponse;
import com.sparta.rooibus.order.application.service.feign.DeliveryService;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.Pagination;
import com.sparta.rooibus.order.domain.repository.OrderRepository;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final UserContextRequestBean userContext;

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
        throw new RuntimeException("주문을 수정하는데에는 권한이 필요합니다.");
    }

    @Transactional
    @CacheEvict(value = "orderCache", key = "#orderId")
    public DeleteOrderResponse deleteOrder(UUID orderId) {
        throw new RuntimeException("주문을 삭제하는데에는 권한이 필요합니다.");
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "orderCache", key = "#orderId")
    public GetOrderResponse getOrder(UUID orderId) {
        UUID userId = userContext.getUserId();

        Order targetOrder = orderRepository.findByUserId(userId,orderId).orElseThrow(
            ()-> new IllegalArgumentException("해당 주문이 없습니다.")
        );
//        TODO : 로그인한 사람의 Id??와 createdAt이 같다면 응답 아니면 에러
        return GetOrderResponse.from(targetOrder);
    }

    @Override
    @Cacheable(value = "searchOrderCache", key = "#searchRequest.keyword() + ':' + #searchRequest.filterKey() + ':' + #searchRequest.filterValue() + ':' + #searchRequest.sort() + ':' + #searchRequest.page() + ':' + #searchRequest.size()")
    public SearchOrderResponse searchOrders(SearchRequest searchRequest) {
        UUID userId = userContext.getUserId();
        Pagination<Order> orderPagination = orderRepository.searchOrdersByUserId(searchRequest,userId);
        return SearchOrderResponse.from(orderPagination);
    }
}
