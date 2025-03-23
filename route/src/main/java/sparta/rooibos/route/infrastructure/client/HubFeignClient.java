package sparta.rooibos.route.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sparta.rooibos.route.application.dto.response.Hub.HubClientResponse;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

    @GetMapping("/api/v1/hub/{hubId}")
    HubClientResponse getHubByHubId(@PathVariable UUID hubId);

    @GetMapping("/api/v1/hub/{region}")
    HubClientResponse getHubByRegion(@PathVariable String region);
}
