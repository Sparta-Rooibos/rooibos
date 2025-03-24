package com.sparta.rooibus.delivery.application.service.feign;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface HubService {
    ResponseEntity<UUID> getHubByUser(UUID userId, String role);
}
