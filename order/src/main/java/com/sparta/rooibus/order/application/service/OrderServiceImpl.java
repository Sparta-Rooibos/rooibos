package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.aop.UserContextRequestBean;
import com.sparta.rooibus.order.application.dto.request.*;
import com.sparta.rooibus.order.application.dto.response.*;
import com.sparta.rooibus.order.application.exception.BusinessOrderException;
import com.sparta.rooibus.order.application.exception.custom.OrderErrorCode;
import com.sparta.rooibus.order.application.service.feign.DeliveryService;
import com.sparta.rooibus.order.application.service.feign.HubService;
import com.sparta.rooibus.order.application.service.feign.MessageService;
import com.sparta.rooibus.order.application.service.feign.StockService;
import com.sparta.rooibus.order.domain.entity.Order;
import com.sparta.rooibus.order.domain.model.OrderStatus;
import com.sparta.rooibus.order.domain.model.Pagination;
import com.sparta.rooibus.order.domain.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final HubService hubService;
    private final StockService stockService;
    private final MessageService messageService;
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

        String email = userContext.getEmail();
        String username = userContext.getName();
        String role = userContext.getRole();
        UUID userId = userContext.getUserId();

        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        boolean orderConfirmed = true;
        GetStockResponse stockResponse;
        int stockQuantity;
        try {
            stockResponse = stockService.getStock(email,username,role,request.productId()).getBody();
            stockQuantity= stockResponse.productQuantity();
            orderConfirmed = request.quantity()>stockQuantity?true:false;
        } catch (Exception e) {
            throw new BusinessOrderException(OrderErrorCode.FEIGN_STOCK_ERROR);
        }
        if (orderConfirmed) {
            stockService.updateStock(email,username,role,stockResponse.id(),stockQuantity-request.quantity());
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.DENIED);
            return null;
        }
        CreateDeliveryResponse deliveryFeignResult = null;
        try {
            deliveryFeignResult = deliveryService.createDelivery(userId,username,role,CreateDeliveryRequest.from(order)).getBody();
        } catch (Exception e) {
            throw new BusinessOrderException(OrderErrorCode.FEIGN_DELIVERY_ERROR);
        }
        UUID deliveryId = deliveryFeignResult.deliveryId();
        UUID departureId = deliveryFeignResult.departure();
        order.setStatus(OrderStatus.SHIPPED);
        order.setDeliveryInfo(deliveryId,departureId);

        messageService.createMessage(email,username,role,
            CreateMessageRequest.of(request.requestClientId().toString(),request.receiveClientId().toString(),
            "상품번호"+request.productId()+"을 "+request.quantity()+"개 주문 완료했습니다."));
        return CreateOrderResponse.from(order);
    }

    @Transactional
    @CachePut(value = "orderCache", key = "#request.id()")
    public UpdateOrderResponse updateOrder(UpdateOrderRequest request) throws BadRequestException {
        if(userContext.getRole().equals("Role_Delivery")||userContext.getRole().equals("Role_ClientManager")) {
            throw new BadRequestException("권한이 필요합니다");
        }

        Order targetOrder = findOrder(request.id());
        targetOrder.update(
            request.status()
        );
        String feignRole = "ROLE_MASTER";
        if(request.status()==OrderStatus.CANCELED){
            deliveryService.cancelDelivery(UpdateDeliveryRequest.of(targetOrder.getDeliveryId(),"CANCELLED"),feignRole);
        }

        return UpdateOrderResponse.from(targetOrder);
    }



    @Transactional
    @CacheEvict(value = "orderCache", key = "#orderId")
    public DeleteOrderResponse deleteOrder(UUID orderId) throws BadRequestException {
        if(userContext.getRole().equals("Role_Delivery")||userContext.getRole().equals("Role_ClientManager")) {
            throw new BadRequestException("권한이 필요합니다");
        }

        Order targetOrder = findOrder(orderId);
        targetOrder.delete(userContext.getUserId().toString());
        String feignRole = "ROLE_MASTER";
        deliveryService.deleteDelivery(targetOrder.getDeliveryId(),feignRole);
        return DeleteOrderResponse.from(targetOrder);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "orderCache", key = "#orderId")
    public GetOrderResponse getOrder(UUID orderId) throws BadRequestException {
        Order targetOrder = findOrder(orderId);
        return GetOrderResponse.from(targetOrder);
    }

    @Override
    @Cacheable(value = "searchOrderCache", key = "#searchRequest.keyword() + ':' + #searchRequest.filterKey() + ':' + #searchRequest.filterValue() + ':' + #searchRequest.sort() + ':' + #searchRequest.page() + ':' + #searchRequest.size()")
    public SearchOrderResponse searchOrders(SearchRequest searchRequest) throws BadRequestException {
        Pagination<Order> orderPagination = search(searchRequest);
        return SearchOrderResponse.from(orderPagination);
    }

    private Order findOrder(UUID orderId) throws BadRequestException {
        String role = userContext.getRole();
        UUID userId = userContext.getUserId();

        switch (role) {
            case "Role_Master" -> {
                log.error("Master User role: {}", role);
                return orderRepository.findById(orderId)
                    .orElseThrow(() -> new BusinessOrderException(OrderErrorCode.ORDER_NOT_FOUND));
            }
            case "Role_HubManager" -> {
                UUID hubId = null;
                try {
                    hubId = hubService.getHubByUser(userContext.getUserId(),
                        userContext.getRole());
                } catch (Exception e) {
                    throw new BusinessOrderException(OrderErrorCode.FEIGN_HUB_ERROR);
                }
                return orderRepository.findByIdAndHub(orderId, hubId)
                    .orElseThrow(() -> new BusinessOrderException(OrderErrorCode.ORDER_NOT_FOUND));
            }
            case "Role_Deliver", "Role_ClientManager" -> {
                return orderRepository.findByUserId(userId, orderId)
                    .orElseThrow(() -> new BusinessOrderException(OrderErrorCode.ORDER_NOT_FOUND));
            }
            default -> {
                log.error("User role: {}", role);
                throw new BadRequestException("권한이 필요합니다");
            }
        }
    }

    private Pagination<Order> search(SearchRequest searchRequest) throws BadRequestException {
        String role = userContext.getRole();
        UUID userId = userContext.getUserId();
        String keyword = searchRequest.keyword();
        String filterKey = searchRequest.filterKey();
        String filterValue = searchRequest.filterValue();
        String sort = searchRequest.sort();
        int page = searchRequest.page();
        int size = searchRequest.size();

        switch (role) {
            case "Role_Master" -> {
                return orderRepository.searchOrders(
                    keyword,filterKey,filterValue,sort,page,size);
            }
            case "Role_HubManager" -> {
                UUID hubId = hubService.getHubByUser(userContext.getUserId(),
                    userContext.getRole());
                return orderRepository.searchOrdersByHubId(keyword,filterKey,filterValue,sort,page,size,hubId);
            }
            case "Role_Deliver", "Role_ClientManager" -> {
                return orderRepository.searchOrdersByUserId(keyword,filterKey,filterValue,sort,page,size,userId);
            }
            default -> throw new BadRequestException("권한이 필요합니다");
        }
    }
}
