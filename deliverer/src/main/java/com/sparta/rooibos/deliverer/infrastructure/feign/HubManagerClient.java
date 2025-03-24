package com.sparta.rooibos.deliverer.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubManagerClient {

    @GetMapping("/api/v1/hubManager/email")
    UUID getHubIdByEmail(@RequestParam String email);
}
