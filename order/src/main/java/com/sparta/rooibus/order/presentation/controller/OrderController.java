package com.sparta.rooibus.order.presentation.controller;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibus.order.application.dto.request.SearchOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponseDTO;

import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponse;
import com.sparta.rooibus.order.application.service.OrderService;
import com.sparta.rooibus.order.presentation.factory.OrderServiceFactory;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceFactory orderServiceFactory;

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderRequest request, @RequestHeader String role) {
        OrderService orderService = orderServiceFactory.getService(role);
        //TODO : 헤더의 'role' 로 권한 체크 하는거 맞는지 확인
        CreateOrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(
            "Order(" + response.orderId() + ") " + ",Delivery(" +
                response.deliveryId() + ") is created"
        );
    }

    @PutMapping
    public ResponseEntity<UpdateOrderResponse> updateOrder(@Valid @RequestBody UpdateOrderRequest request,@RequestHeader String role){
        OrderService orderService = orderServiceFactory.getService(role);
        UpdateOrderResponse response = orderService.updateOrder(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<DeleteOrderResponse> deleteOrder(@PathVariable UUID orderId,@RequestHeader String role){
        OrderService orderService = orderServiceFactory.getService(role);
        DeleteOrderResponse response = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponseDTO> getOrder(@PathVariable("orderId")UUID orderId,@RequestHeader String role){
        OrderService orderService = orderServiceFactory.getService(role);
        GetOrderResponseDTO response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public Page<SearchOrderResponseDTO> searchOrder(
        @RequestParam(required = false, defaultValue = "") String keyword,
        @RequestParam(required = false, defaultValue = "") String filterKey,
        @RequestParam(required = false, defaultValue = "") String filterValue,
        @RequestParam(required = false, defaultValue = "asc") String sort,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,@RequestHeader String role
    ) {
        OrderService orderService = orderServiceFactory.getService(role);
        SearchOrderRequestDTO requestDTO = new SearchOrderRequestDTO(
            keyword, filterKey, filterValue, sort, page, size
        );
        return null;
    }
}
