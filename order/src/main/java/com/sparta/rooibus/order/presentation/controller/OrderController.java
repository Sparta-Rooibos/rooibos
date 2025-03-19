package com.sparta.rooibus.order.presentation.controller;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequest;
import com.sparta.rooibus.order.application.dto.request.SearchOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponseDTO;
import com.sparta.rooibus.order.application.service.MasterOrderService;

import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequest;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponse;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponseDTO;
import com.sparta.rooibus.order.application.service.OrderService;
import com.sparta.rooibus.order.presentation.factory.OrderServiceFactory;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        OrderService orderService = orderServiceFactory.getService("Role_Master");
        //TODO : 임시로 마스터 해놓음 - role로 해주기  확인은 해야하니까
        CreateOrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(
            "Order(" + response.orderId() + ") " + ",Delivery(" +
                response.deliveryId() + ") is created"
        );
    }

    @PutMapping
    public ResponseEntity<UpdateOrderResponse> updateOrder(@Valid @RequestBody UpdateOrderRequest request,@RequestHeader String role){
        OrderService orderService = orderServiceFactory.getService("Role_Master");
        UpdateOrderResponse response = orderService.updateOrder(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<DeleteOrderResponseDTO> deleteOrder(@PathVariable("orderId")UUID orderId,@RequestHeader String role){
        OrderService orderService = orderServiceFactory.getService("Role_Master");
        DeleteOrderResponseDTO response = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponseDTO> getOrder(@PathVariable("orderId")UUID orderId,@RequestHeader String role){
        OrderService orderService = orderServiceFactory.getService("Role_Master");
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
        OrderService orderService = orderServiceFactory.getService("Role_Master");
        SearchOrderRequestDTO requestDTO = new SearchOrderRequestDTO(
            keyword, filterKey, filterValue, sort, page, size
        );
        return null;
    }
}
