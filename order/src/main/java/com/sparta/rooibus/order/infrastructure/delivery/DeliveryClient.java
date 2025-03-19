package com.sparta.rooibus.order.infrastructure.delivery;

import com.sparta.rooibus.order.application.dto.request.DeliveryRequestDTO;
import com.sparta.rooibus.order.application.dto.response.DeliveryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "delivery-service", url = "/api/v1/deliveries")
//public interface DeliveryClient extends FeignDeliveryService { TODO : feign 적용시 주석 제거
public interface DeliveryClient  {
    @PostMapping
    ResponseEntity<DeliveryResponseDTO> createDelivery(DeliveryRequestDTO request);
}