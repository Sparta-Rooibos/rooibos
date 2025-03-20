package com.sparta.rooibus.order.application.service;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface HubService {

    UUID getHubByUser(UUID userId, String role);
}
