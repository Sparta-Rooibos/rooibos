package com.sparta.rooibus.order.presentation.controller;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponseDTO;
import com.sparta.rooibus.order.application.service.OrderService;

import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.request.UpdateOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.request.DeleteOrderResponseDTO;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
// TODO : ResponseEntity 타입 String -> responseDTO 해야할까 물어보기
    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderRequestDTO request) {
        CreateOrderResponseDTO response = orderService.createOrder(request);
        return ResponseEntity.ok(
            "Order(" + response.orderId() + ") " + ",Delivery(" +
                response.deliveryId() + ") is created"
        );
    }

    @PutMapping
    public ResponseEntity<UpdateOrderResponseDTO> updateOrder(@Valid @RequestBody UpdateOrderRequestDTO request){
        UpdateOrderResponseDTO response = orderService.updateOrder(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<DeleteOrderResponseDTO> deleteOrder(@PathVariable("orderId")UUID orderId){
        DeleteOrderResponseDTO response = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(response);
    }
}
