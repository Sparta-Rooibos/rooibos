package sparta.rooibos.route.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import sparta.rooibos.route.application.dto.request.direction.GetGeoDirectionRequest;
import sparta.rooibos.route.application.dto.response.direction.GetGeoDirectionResponse;
import sparta.rooibos.route.infrastructure.config.NaverFeignClientConfig;

@FeignClient(
        name = "naverGeoDirectionClient",
        url = "https://maps.apigw.ntruss.com",
        configuration = NaverFeignClientConfig.class
)
public interface NaverGeoDirectionClient {
    @GetMapping(value = "/driving", consumes = "application/json")
    GetGeoDirectionResponse getDirection(GetGeoDirectionRequest getGeoDirectionRequest);
}
