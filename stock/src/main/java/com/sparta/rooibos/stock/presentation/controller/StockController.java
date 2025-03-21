package com.sparta.rooibos.stock.presentation.controller;

import com.sparta.rooibos.stock.application.dto.request.CreateStockRequest;
import com.sparta.rooibos.stock.application.dto.request.SearchStockRequest;
import com.sparta.rooibos.stock.application.dto.request.UpdateStockRequest;
import com.sparta.rooibos.stock.application.dto.response.CreateStockResponse;
import com.sparta.rooibos.stock.application.dto.response.GetStockResponse;
import com.sparta.rooibos.stock.application.dto.response.SearchStockResponse;
import com.sparta.rooibos.stock.application.service.StockService;
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
    public ResponseEntity<SearchStockResponse> searchStock(@ModelAttribute SearchStockRequest request) {
        return ResponseEntity.ok(stockService.searchStock(request));
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<GetStockResponse> getStock(@PathVariable UUID stockId) {
        return ResponseEntity.ok(stockService.getStock(stockId));
    }

    @PostMapping
    public ResponseEntity<CreateStockResponse> createStock(@RequestBody CreateStockRequest request) {
        return ResponseEntity.ok(stockService.createStock(request));
    }

    @PutMapping("/{stockId}")
    public ResponseEntity<Void> updateStock(@PathVariable UUID stockId, @RequestBody UpdateStockRequest request) {
        stockService.updateStock(stockId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{stockId}")
    public ResponseEntity<Void> deleteStock(@PathVariable UUID stockId) {
        stockService.deleteStock(stockId);
        return ResponseEntity.ok().build();
    }
}
