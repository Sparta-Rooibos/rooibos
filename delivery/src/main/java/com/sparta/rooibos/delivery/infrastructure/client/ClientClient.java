package com.sparta.rooibos.delivery.infrastructure.client;

import com.sparta.rooibos.delivery.application.dto.response.feign.client.GetClientManagerResponse;
import com.sparta.rooibos.delivery.application.dto.response.feign.client.GetClientResponse;
import com.sparta.rooibos.delivery.application.service.feign.ClientService;
import com.sparta.rooibos.delivery.infrastructure.config.FeignRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@Primary
@FeignClient(name = "client-service", url = "http://localhost:19094",configuration = FeignRetryConfig.class)
public interface ClientClient extends ClientService {
    @GetMapping("/api/v1/client/{clientId}")
    ResponseEntity<GetClientResponse> getClient(
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @PathVariable("clientId") UUID clientId
    );

    @GetMapping("/api/v1/client/manager/{clientId}")
    ResponseEntity<GetClientManagerResponse> getClientManager(
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @PathVariable("clientId") UUID clientId
    );
}
