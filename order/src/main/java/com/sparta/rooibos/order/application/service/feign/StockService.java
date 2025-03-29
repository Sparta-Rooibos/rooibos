package com.sparta.rooibos.order.application.service.feign;

import com.sparta.rooibos.order.application.service.GetStockResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface StockService {
    ResponseEntity<GetStockResponse> getStock(String email, String username, String role, UUID uuid);

    ResponseEntity<Void> updateStock(String email, String username, String role, UUID id, int quantity);
}
