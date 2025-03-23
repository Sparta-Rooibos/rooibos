package com.sparta.rooibus.order.presentation.controller;

import com.sparta.rooibus.order.application.dto.request.CreateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.request.SearchOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.CreateOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.GetOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.SearchOrderResponseDTO;
import com.sparta.rooibus.order.application.service.OrderService;

import com.sparta.rooibus.order.application.dto.request.UpdateOrderRequestDTO;
import com.sparta.rooibus.order.application.dto.response.UpdateOrderResponseDTO;
import com.sparta.rooibus.order.application.dto.response.DeleteOrderResponseDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
// TODO : ResponseEntity 타입 String -> responseDTO 해야할까 물어보기, page같은 경우는 dto필요하다고 생각함. 근데 나머지는?
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

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponseDTO> getOrder(@PathVariable("orderId")UUID orderId){
        GetOrderResponseDTO response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public Page<SearchOrderResponseDTO> searchOrders(
        @RequestParam(required = false, defaultValue = "") String keyword,
        @RequestParam(required = false, defaultValue = "") String filterKey,
        @RequestParam(required = false, defaultValue = "") String filterValue,
        @RequestParam(required = false, defaultValue = "asc") String sort,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size) {

        SearchOrderRequestDTO requestDTO = new SearchOrderRequestDTO(
            keyword, filterKey, filterValue, sort, page, size
        );

        return orderService.searchOrders(requestDTO);
    }
}
