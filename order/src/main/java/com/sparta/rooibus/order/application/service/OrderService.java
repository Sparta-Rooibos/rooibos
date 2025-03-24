package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibus.order.application.dto.request.SearchRequest;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponse;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponse;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponse;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponse;
import java.util.UUID;
import org.apache.coyote.BadRequestException;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);
    UpdateOrderResponse updateOrder(UpdateOrderRequest request) throws BadRequestException;
    DeleteOrderResponse deleteOrder(UUID orderId) throws BadRequestException;
    GetOrderResponse getOrder(UUID orderId) throws BadRequestException;
    SearchOrderResponse searchOrders(SearchRequest searchRequest) throws BadRequestException;
}
