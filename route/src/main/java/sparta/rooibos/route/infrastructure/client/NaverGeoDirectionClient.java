package sparta.rooibos.route.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sparta.rooibos.route.application.dto.response.direction.GetGeoDirectionResponse;
import sparta.rooibos.route.infrastructure.config.NaverFeignClientConfig;

@FeignClient(
        name = "naverGeoDirectionClient",
        url = "https://maps.apigw.ntruss.com",
        configuration = NaverFeignClientConfig.class
)
public interface NaverGeoDirectionClient {
    @GetMapping(value = "/map-direction/v1/driving", consumes = "application/json")
    GetGeoDirectionResponse getDirection(@RequestParam String start, @RequestParam String goal);
}
