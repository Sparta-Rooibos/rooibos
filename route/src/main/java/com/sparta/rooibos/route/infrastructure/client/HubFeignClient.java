package com.sparta.rooibos.route.infrastructure.client;

import com.sparta.rooibos.route.application.dto.response.hub.HubClientResponse;
import com.sparta.rooibos.route.infrastructure.config.FeignClientRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "hub-service", configuration = FeignClientRetryConfig.class)
public interface HubFeignClient {

    @GetMapping("/api/v1/hub/{hubId}")
    HubClientResponse getHubByHubId(@PathVariable UUID hubId);

    @GetMapping("/api/v1/hub/region")
    HubClientResponse getHubByRegion(@RequestParam String region);
}
