package com.sparta.rooibus.order.application.port;

import com.sparta.rooibus.order.application.dto.request.FeignDeliveryRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@FeignClient(name = "delivery-service", url = "/api/v1/deliveries")
public interface FeignDeliveryClient {
// feign으로 배달 서비스 기능 요청
    @PostMapping
    void createDelivery(FeignDeliveryRequestDTO request);
}
