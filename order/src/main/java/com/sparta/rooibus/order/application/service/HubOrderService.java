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
public class HubOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final HubService hubService;
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
//        TODO : 허브로 feign 요청
        UUID hubId = hubService.getHubByUser(userContext.getUserId(),userContext.getRole());

        Order targetOrder = orderRepository.findByIdAndHub(request.id(),hubId).orElseThrow(
            ()-> new IllegalArgumentException("주문을 찾을 수 없습니다.")
        );

        targetOrder.update(
            request.receiveClientId(),
            request.quantity(),
            request.requirement()
        );

        return UpdateOrderResponse.from(targetOrder);
    }

    @Transactional
    @CacheEvict(value = "orderCache", key = "#orderId")
    public DeleteOrderResponse deleteOrder(UUID orderId) {
        UUID hubId = hubService.getHubByUser(userContext.getUserId(),userContext.getRole());

        Order targetOrder = orderRepository.findByIdAndHub(orderId,hubId).orElseThrow(
            ()-> new IllegalArgumentException("삭제할 주문이 없습니다.")
        );

        targetOrder.delete();

        return DeleteOrderResponse.from(targetOrder);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "orderCache", key = "#orderId")
    public GetOrderResponse getOrder(UUID orderId) {
        UUID hubId = hubService.getHubByUser(userContext.getUserId(),userContext.getRole());

        Order targetOrder = orderRepository.findByIdAndHub(orderId,hubId).orElseThrow(
            ()-> new IllegalArgumentException("해당 주문이 없습니다.")
        );

        return GetOrderResponse.from(targetOrder);
    }

    @Override
    @Cacheable(value = "searchOrderCache", key = "#searchRequest.keyword() + ':' + #searchRequest.filterKey() + ':' + #searchRequest.filterValue() + ':' + #searchRequest.sort() + ':' + #searchRequest.page() + ':' + #searchRequest.size()")
    public SearchOrderResponse searchOrders(SearchRequest searchRequest) {
        UUID hubId = hubService.getHubByUser(userContext.getUserId(),userContext.getRole());

        Pagination<Order> orderPagination = orderRepository.searchOrdersByHubId(searchRequest,hubId);
        return SearchOrderResponse.from(orderPagination);
    }
}
