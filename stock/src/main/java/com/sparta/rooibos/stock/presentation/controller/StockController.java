package com.sparta.rooibos.stock.presentation.controller;

import com.sparta.rooibos.stock.application.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;
    
    @GetMapping
    public void searchStock() {
        stockService.searchStock();
    }
    @GetMapping
    public void getStock() {
        stockService.getStock();
    }
    @PostMapping
    public void createStock() {
        stockService.createStock();
    }
    @PutMapping
    public void updateStock() {
        stockService.updateStock();
    }

    @PatchMapping
    public void deleteStock() {
        stockService.deleteStock();
    }
}
