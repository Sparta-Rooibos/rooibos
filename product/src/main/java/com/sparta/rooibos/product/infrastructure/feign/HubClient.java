package com.sparta.rooibos.product.infrastructure.feign;

import com.sparta.rooibos.product.application.feign.service.ClientService;
import com.sparta.rooibos.product.application.feign.service.HubService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient extends HubService {

    @GetMapping("/api/v1/hubManager")
    UUID getHubManager(@RequestParam String email);
}
