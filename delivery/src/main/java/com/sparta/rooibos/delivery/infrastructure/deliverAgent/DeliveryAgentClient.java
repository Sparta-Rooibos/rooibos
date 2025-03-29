package com.sparta.rooibos.delivery.infrastructure.deliverAgent;

import com.sparta.rooibos.delivery.application.dto.response.feign.deliverAgent.GetDeliverResponse;
import com.sparta.rooibos.delivery.application.service.feign.DeliveryAgentService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Primary
@FeignClient(name = "deliverer", url = "http://localhost:19098")
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
