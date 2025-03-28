package com.sparta.rooibos.deliverer.application.feign;

import java.util.UUID;

public interface HubService {
    UUID getHubIdByEmail(String email);
    boolean checkHub(UUID hubId);
}
