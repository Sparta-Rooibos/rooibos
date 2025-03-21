package com.sparta.rooibos.client.infrastructure.feign;

import com.sparta.rooibos.client.application.feigin.service.HubService;
import com.sparta.rooibos.client.domain.fegin.hub.model.Hub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service")
public interface HubClient extends HubService {


    @GetMapping("/api/v1/hub/{hubId}")
    Hub getHub(@PathVariable String hubId);
}
