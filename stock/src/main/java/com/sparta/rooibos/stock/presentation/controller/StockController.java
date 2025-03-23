package com.sparta.rooibos.stock.presentation.controller;

import com.sparta.rooibos.stock.application.annotation.RoleCheck;
import com.sparta.rooibos.stock.application.dto.request.CreateStockRequest;
import com.sparta.rooibos.stock.application.dto.request.SearchStockRequest;
import com.sparta.rooibos.stock.application.dto.request.UpdateStockRequest;
import com.sparta.rooibos.stock.application.dto.response.CreateStockResponse;
import com.sparta.rooibos.stock.application.dto.response.GetStockResponse;
import com.sparta.rooibos.stock.application.dto.response.SearchStockResponse;
import com.sparta.rooibos.stock.application.service.StockService;
import com.sparta.rooibos.stock.application.type.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping
    @RoleCheck({Role.MASTER, Role.HUB, Role.DELIVERY, Role.CLIENT})
    public ResponseEntity<SearchStockResponse> searchStock(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @ModelAttribute SearchStockRequest request) {
        return ResponseEntity.ok(stockService.searchStock(request));
    }

    @GetMapping("/{stockId}")
    @RoleCheck({Role.MASTER, Role.HUB, Role.DELIVERY, Role.CLIENT})
    public ResponseEntity<GetStockResponse> getStock(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID stockId) {
        return ResponseEntity.ok(stockService.getStock(stockId));
    }

    @PostMapping
    @RoleCheck({Role.MASTER, Role.HUB})
    public ResponseEntity<CreateStockResponse> createStock(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @RequestBody @Valid CreateStockRequest request) {
        return ResponseEntity.ok(stockService.createStock(email, request));
    }

    @PutMapping("/{stockId}")
    @RoleCheck({Role.MASTER, Role.HUB})
    public ResponseEntity<Void> updateStock(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID stockId, @RequestBody @Valid UpdateStockRequest request) {
        stockService.updateStock(email, stockId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{stockId}")
    @RoleCheck({Role.MASTER, Role.HUB})
    public ResponseEntity<Void> deleteStock(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Name") String username,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID stockId) {
        stockService.deleteStock(email, stockId);
        return ResponseEntity.ok().build();
    }
}
