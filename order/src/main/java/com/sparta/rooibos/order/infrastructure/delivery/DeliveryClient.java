package com.sparta.rooibos.order.infrastructure.delivery;

import com.sparta.rooibos.order.application.dto.request.CreateDeliveryRequest;
import com.sparta.rooibos.order.application.dto.request.UpdateDeliveryRequest;
import com.sparta.rooibos.order.application.dto.response.CreateDeliveryResponse;
import com.sparta.rooibos.order.application.service.feign.DeliveryService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Primary
@FeignClient(name = "delivery-service", url = "http://localhost:19097")
public interface DeliveryClient extends DeliveryService {
    @PostMapping("/api/v1/deliveries")
    ResponseEntity<CreateDeliveryResponse> createDelivery(
        @RequestHeader("X-User-Id") UUID userId,
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @RequestBody CreateDeliveryRequest request);

    @PutMapping
    ResponseEntity<UpdateDeliveryResponse> cancelDelivery(
        @RequestBody UpdateDeliveryRequest request,
        @RequestHeader("X-User-Role") String feignRole
    );

    @DeleteMapping("/{deliveryId}")
    ResponseEntity<UUID> deleteDelivery(
        @PathVariable("deliveryId") UUID deliveryId,
        @RequestHeader("X-User-Role") String role
    );

}