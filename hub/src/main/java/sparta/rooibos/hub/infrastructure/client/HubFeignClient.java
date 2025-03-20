package sparta.rooibos.hub.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sparta.rooibos.hub.application.dto.response.GetHubResponse;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

    @GetMapping("/api/v1/hub/{hubId}")
    GetHubResponse getHub(@PathVariable UUID hubId);
}
