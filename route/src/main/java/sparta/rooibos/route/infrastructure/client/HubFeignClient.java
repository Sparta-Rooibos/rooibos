package sparta.rooibos.route.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

    @GetMapping("/api/v1/hub")
    UUID getHubIdByRegion(@RequestParam String region);
}
