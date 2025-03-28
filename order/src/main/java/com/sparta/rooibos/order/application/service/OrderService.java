package com.sparta.rooibos.order.application.service;

import com.sparta.rooibos.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibos.order.application.dto.request.SearchRequest;
import com.sparta.rooibos.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibos.order.application.dto.response.*;
import org.apache.coyote.BadRequestException;

import java.util.UUID;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);
    UpdateOrderResponse updateOrder(UpdateOrderRequest request) throws BadRequestException;
    DeleteOrderResponse deleteOrder(UUID orderId) throws BadRequestException;
    GetOrderResponse getOrder(UUID orderId) throws BadRequestException;
    SearchOrderResponse searchOrders(SearchRequest searchRequest) throws BadRequestException;
}
