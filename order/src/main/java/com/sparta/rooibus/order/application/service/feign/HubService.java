package com.sparta.rooibus.order.application.service.feign;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface HubService {
    UUID getHubByUser(UUID userId, String role);
}
