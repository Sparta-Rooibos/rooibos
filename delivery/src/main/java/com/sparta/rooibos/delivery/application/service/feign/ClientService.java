package com.sparta.rooibos.delivery.application.service.feign;

import com.sparta.rooibos.delivery.application.dto.response.feign.client.GetClientManagerResponse;
import com.sparta.rooibos.delivery.application.dto.response.feign.client.GetClientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface ClientService {
    ResponseEntity<GetClientResponse> getClient(
        String email,
        String username,
        String role,
        UUID clientId);

    ResponseEntity<GetClientManagerResponse> getClientManager(
        String email,
        String username,
        String role,
        UUID clientId
    );
}
