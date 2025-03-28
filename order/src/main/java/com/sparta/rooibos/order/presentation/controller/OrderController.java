package com.sparta.rooibos.order.presentation.controller;

import com.sparta.rooibos.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibos.order.application.dto.request.SearchRequest;
import com.sparta.rooibos.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibos.order.application.dto.response.*;
import com.sparta.rooibos.order.application.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(
            "Order(" + response.orderId() + ") " + ",Delivery(" +
                response.deliveryId() + ") is created"
        );
    }

    @PutMapping
    public ResponseEntity<UpdateOrderResponse> updateOrder(@Valid @RequestBody UpdateOrderRequest request)
        throws BadRequestException {
        UpdateOrderResponse response = orderService.updateOrder(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<DeleteOrderResponse> deleteOrder(@PathVariable UUID orderId)
        throws BadRequestException {
        DeleteOrderResponse response = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable UUID orderId)
        throws BadRequestException {
        GetOrderResponse response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchOrderResponse> searchOrder(
        @ModelAttribute SearchRequest request
    ) throws BadRequestException {
        return ResponseEntity.ok(orderService.searchOrders(request));
    }
}
