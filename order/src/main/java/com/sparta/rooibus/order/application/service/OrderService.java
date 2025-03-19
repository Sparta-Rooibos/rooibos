package com.sparta.rooibus.order.application.service;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibus.order.application.dto.request.SearchOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponse;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponse;
import com.sparta.rooibus.order.domain.model.Pagination;
import java.util.UUID;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);
    UpdateOrderResponse updateOrder(UpdateOrderRequest request);
    DeleteOrderResponse deleteOrder(UUID orderId);
    GetOrderResponseDTO getOrder(UUID orderId);
    Pagination<SearchOrderResponseDTO> searchOrders(SearchOrderRequestDTO request);
}
