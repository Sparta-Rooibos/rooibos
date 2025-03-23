package com.sparta.rooibos.product.infrastructure.feign;

import com.sparta.rooibos.product.domain.feign.dto.Client;
import com.sparta.rooibos.product.application.feign.service.ClientService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(name = "client-service")
public interface ClientClient extends ClientService {
    @GetMapping("/api/v1/client/{clientId}")
    Optional<Client> getClient(@RequestHeader(name = "X-User-EMAIL") String email,
                               @RequestHeader(name = "X-User-Name") String name,
                               @RequestHeader(name = "X-User-Role") String role,
                               @PathVariable String clientId);

    @GetMapping("/api/v1/client/manager")
    String getClientId(@RequestHeader(name = "X-User-Email") String email);
}
