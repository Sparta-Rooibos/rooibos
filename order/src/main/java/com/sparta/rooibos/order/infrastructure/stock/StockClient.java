package com.sparta.rooibos.order.infrastructure.stock;

import com.sparta.rooibos.order.application.service.GetStockResponse;
import com.sparta.rooibos.order.application.service.feign.StockService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@Primary
@FeignClient(name = "stock-service", url = "http://localhost:19102")
public interface StockClient extends StockService {
    @GetMapping("/api/v1/stock/{productId}")
    ResponseEntity<GetStockResponse> getStock(
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @PathVariable("productId") UUID productId);

    @PutMapping("/api/v1/stock/{stockId}")
    ResponseEntity<Void> updateStock(
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @PathVariable UUID stockId,int quantity
    );
}
