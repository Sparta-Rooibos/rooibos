package com.sparta.rooibos.delivery.infrastructure.hub;

import com.sparta.rooibos.delivery.application.service.feign.HubService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@Primary
@FeignClient(name = "hub-service", url = "http://localhost:19100")
public interface HubClient extends HubService {

    @GetMapping("/api/v1/hubManager/{userId}")
    ResponseEntity<UUID> getHubByUser(
        @PathVariable("userId") UUID userId,
        @RequestHeader("X-User-Role")String role);
}
