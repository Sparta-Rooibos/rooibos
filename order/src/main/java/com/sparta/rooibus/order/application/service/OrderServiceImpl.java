package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.aop.UserContextRequestBean;
import com.sparta.rooibus.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibus.order.application.dto.request.SearchRequest;
import com.sparta.rooibus.order.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponse;
import com.sparta.rooibus.order.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponse;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponse;
import com.sparta.rooibus.order.application.exception.BusinessOrderException;
import com.sparta.rooibus.order.application.exception.custom.OrderErrorCode;
import com.sparta.rooibus.order.application.service.feign.DeliveryService;
import com.sparta.rooibus.order.application.service.feign.HubService;
import com.sparta.rooibus.order.application.service.feign.StockService;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.OrderStatus;
import com.sparta.rooibus.order.domain.model.Pagination;
import com.sparta.rooibus.order.domain.repository.OrderRepository;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponse;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final HubService hubService;
    private final StockService stockService;
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
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        boolean orderConfirmed = false;
        try {
            orderConfirmed = stockService.checkStock(request.productId(),request.quantity());
        } catch (Exception e) {
            throw new BusinessOrderException(OrderErrorCode.FEIGN_STOCK_ERROR);
        }
        if (orderConfirmed) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.CANCELED);
            return null;
        }

        CreateDeliveryResponse deliveryFeignResult = null;
        try {
            deliveryFeignResult = deliveryService.createDelivery(CreateDeliveryRequest.from(order),
                userContext.getRole());
        } catch (Exception e) {
            throw new BusinessOrderException(OrderErrorCode.FEIGN_DELIVERY_ERROR);
        }
        UUID deliveryId = deliveryFeignResult.deliveryId();
        UUID departureId = deliveryFeignResult.departure();
        order.setStatus(OrderStatus.SHIPPED);
        order.setDeliveryInfo(deliveryId,departureId);

//        TODO : 슬랙 메세지 보내기
        return CreateOrderResponse.from(order);
    }

    @Transactional
    @CachePut(value = "orderCache", key = "#request.id()")
    public UpdateOrderResponse updateOrder(UpdateOrderRequest request) {
        UUID userId = userContext.getUserId();
        String role = userContext.getRole();

        Order targetOrder = getOrderByRole(userId,role,request.id());

        targetOrder.update(
            request.receiveClientId(),
            request.quantity(),
            request.requirement()
        );

        return UpdateOrderResponse.from(targetOrder);
    }

    private Order getOrderByRole(UUID userId,String role,UUID orderId){
        if(role.equals("Role_Master")){
            return  orderRepository.findById(orderId)
                .orElseThrow(()->new BusinessOrderException(OrderErrorCode.ORDER_NOT_FOUND));
        }else if(role.equals("Role_HubManager")){
            UUID hubId = hubService.getHubByUser(userContext.getUserId(),userContext.getRole());
            return orderRepository.findByIdAndHub(orderId,hubId)
                .orElseThrow(()->new BusinessOrderException(OrderErrorCode.ORDER_NOT_FOUND));
        }else if(role.equals("Role_Deliver")){
            return orderRepository.findByUserId(userId,orderId)
                .orElseThrow(()->new BusinessOrderException(OrderErrorCode.ORDER_NOT_FOUND));
        }else{
            return orderRepository.findByUserId(userId,orderId)
                .orElseThrow(()->new BusinessOrderException(OrderErrorCode.ORDER_NOT_FOUND));
        }
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

    @Override
    @Cacheable(value = "searchOrderCache", key = "#searchRequest.keyword() + ':' + #searchRequest.filterKey() + ':' + #searchRequest.filterValue() + ':' + #searchRequest.sort() + ':' + #searchRequest.page() + ':' + #searchRequest.size()")
    public SearchOrderResponse searchOrders(SearchRequest searchRequest) {
        Pagination<Order> orderPagination = orderRepository.searchOrders(searchRequest);
        return SearchOrderResponse.from(orderPagination);
    }
}
