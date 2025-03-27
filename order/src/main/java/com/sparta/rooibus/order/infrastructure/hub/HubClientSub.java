package com.sparta.rooibus.order.infrastructure.hub;

import com.sparta.rooibus.order.application.service.feign.HubService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HubClientSub implements HubService {

    @Override
    public UUID getHubByUser(UUID userId, String role) {
        return UUID.randomUUID();
    }
}
