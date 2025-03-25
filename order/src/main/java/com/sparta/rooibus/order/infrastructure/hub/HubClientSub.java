package com.sparta.rooibus.order.infrastructure.hub;

import com.sparta.rooibus.order.application.service.feign.HubService;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class HubClientSub implements HubService {

    @Override
    public UUID getHubByUser(UUID userId, String role) {
        return UUID.randomUUID();
    }
}
