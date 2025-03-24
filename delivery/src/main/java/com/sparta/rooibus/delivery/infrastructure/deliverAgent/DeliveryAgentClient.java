package com.sparta.rooibus.delivery.infrastructure.deliverAgent;

import com.sparta.rooibus.delivery.application.dto.response.feign.deliverAgent.GetDeliverResponse;
import com.sparta.rooibus.delivery.application.service.feign.DeliveryAgentService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Primary
@FeignClient(name = "user-service", url = "http://localhost:19098")
public interface DeliveryAgentClient extends DeliveryAgentService {
    @GetMapping("/api/v1/deliverers/assign}")
    GetDeliverResponse getDeliver(
        @RequestParam UUID hubId,
        @RequestParam String type,
        @RequestHeader("X-User-Role") String role);

    @PatchMapping("/api/v1/deliverers/unassign/{delivererId}}")
    void cancelDeliver(
        @PathVariable("delivererId") UUID delivererId);
}
