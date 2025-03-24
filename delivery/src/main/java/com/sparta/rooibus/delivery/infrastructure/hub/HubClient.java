package com.sparta.rooibus.delivery.infrastructure.hub;

import com.sparta.rooibus.delivery.application.service.feign.HubService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Primary
@FeignClient(name = "hub-service", url = "http://localhost:19100")
public interface HubClient extends HubService {

    @GetMapping("/api/v1/hubManager/{userId}")
    ResponseEntity<UUID> getHubByUser(
        @PathVariable("userId") UUID userId,
        @RequestHeader("X-User-Role")String role);
}
