package com.sparta.rooibos.deliverer.application.feign;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface HubService {
    UUID getHubIdByEmail(@RequestParam String email);

    boolean checkHub(@PathVariable UUID hubId);
}
