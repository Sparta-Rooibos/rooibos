package com.sparta.rooibus.order.application.service.feign;

import com.sparta.rooibus.order.application.service.GetStockResponse;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface StockService {
    ResponseEntity<GetStockResponse> getStock(String email, String username, String role, UUID uuid);

    ResponseEntity<Void> updateStock(String email, String username, String role, UUID id, int quantity);
}
