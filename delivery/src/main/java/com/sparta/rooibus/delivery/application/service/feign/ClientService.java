package com.sparta.rooibus.delivery.application.service.feign;

import com.sparta.rooibus.delivery.application.dto.request.feign.client.GetClientManagerRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientManagerResponse;
import com.sparta.rooibus.delivery.application.dto.response.feign.client.GetClientResponse;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
