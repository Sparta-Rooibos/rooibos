package com.sparta.rooibos.product.infrastructure.feign;

import com.sparta.rooibos.product.domain.feign.dto.Client;
import com.sparta.rooibos.product.domain.feign.service.ClientService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "client-service")
public interface ClientClient extends ClientService {
    @GetMapping("/api/v1/client/{clientId}")
    Client getClient(@PathVariable String clientId);

    @GetMapping("/api/v1/client/manager")
    String getClientId(@RequestHeader(name = "X-User-Email") String email);
}
