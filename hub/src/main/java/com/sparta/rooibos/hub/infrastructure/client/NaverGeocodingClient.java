package com.sparta.rooibos.hub.infrastructure.client;

import com.sparta.rooibos.hub.application.dto.GeoLocation.response.GetCoordinatesResponse;
import com.sparta.rooibos.hub.infrastructure.config.FeignClientRetryConfig;
import com.sparta.rooibos.hub.infrastructure.config.NaverFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "naverGeocodingClient",
        url = "https://maps.apigw.ntruss.com",
        configuration = {NaverFeignClientConfig.class, FeignClientRetryConfig.class}
)
public interface NaverGeocodingClient {

    @GetMapping(value = "/map-geocode/v2/geocode", consumes = "application/json")
    GetCoordinatesResponse getCoordinates(@RequestParam String query);
}
