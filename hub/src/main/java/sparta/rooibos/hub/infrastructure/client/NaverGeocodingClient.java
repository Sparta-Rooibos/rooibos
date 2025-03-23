package sparta.rooibos.hub.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sparta.rooibos.hub.application.dto.GeoLocation.response.GetCoordinatesResponse;
import sparta.rooibos.hub.infrastructure.config.NaverFeignClientConfig;

@FeignClient(
        name = "naverGeocodingClient",
        url = "https://maps.apigw.ntruss.com",
        configuration = NaverFeignClientConfig.class
)
public interface NaverGeocodingClient {

    @GetMapping(value = "/map-geocode/v2/geocode", consumes = "application/json")
    GetCoordinatesResponse getCoordinates(@RequestParam String query);
}
