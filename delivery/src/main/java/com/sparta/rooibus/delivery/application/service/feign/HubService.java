package com.sparta.rooibus.delivery.application.service.feign;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface HubService {
    UUID getHubByUser(UUID userId, String role);
}
