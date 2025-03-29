package com.sparta.rooibos.delivery.application.service.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface HubService {
    ResponseEntity<UUID> getHubByUser(UUID userId, String role);
}
