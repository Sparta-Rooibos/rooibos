package com.sparta.rooibos.stock.presentation.controller;

import com.sparta.rooibos.stock.application.dto.request.CreateStockRequest;
import com.sparta.rooibos.stock.application.dto.request.SearchStockRequest;
import com.sparta.rooibos.stock.application.dto.request.UpdateStockRequest;
import com.sparta.rooibos.stock.application.dto.response.CreateStockResponse;
import com.sparta.rooibos.stock.application.dto.response.GetStockResponse;
import com.sparta.rooibos.stock.application.dto.response.SearchStockResponse;
import com.sparta.rooibos.stock.application.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping
    public SearchStockResponse searchStock(@ModelAttribute SearchStockRequest request) {
        return stockService.searchStock(request);
    }

    @GetMapping("/{stockId}")
    public GetStockResponse getStock(@PathVariable UUID stockId) {
        return stockService.getStock(stockId);
    }

    @PostMapping
    public CreateStockResponse createStock(@RequestBody CreateStockRequest request) {
        return stockService.createStock(request);
    }

    @PutMapping("/{stockId}")
    public void updateStock(@PathVariable UUID stockId, @RequestBody UpdateStockRequest request) {
        stockService.updateStock(stockId, request);
    }

    @PatchMapping("/{stockId}")
    public void deleteStock(@PathVariable UUID stockId) {
        stockService.deleteStock(stockId);
    }
}
