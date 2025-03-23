package com.sparta.rooibus.delivery.infrastructure.client;

import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientManagerResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientResponse;
import com.sparta.rooibus.delivery.application.service.feign.ClientService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Primary
@FeignClient(name = "client-service", url = "http://localhost:19094")
public interface ClientClient extends ClientService {
    @GetMapping("/client/{clientId}")
    ResponseEntity<GetClientResponse> getClient(
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @PathVariable("clientId") UUID clientId
    );

    @GetMapping("/client/{clientId}")
    ResponseEntity<GetClientManagerResponse> getClientManager(
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @PathVariable("clientId") UUID clientId
    );
}
