package com.sparta.rooibus.delivery.infrastructure.route;


import com.sparta.rooibus.delivery.application.dto.response.feign.route.GetRouteResponse;
import com.sparta.rooibus.delivery.application.service.feign.RouteService;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Primary
@FeignClient(name = "route-service", url = "http://localhost:19100")
public interface RouteClient extends RouteService {
    @GetMapping("/api/v1/route/recommend")
    ResponseEntity<GetRouteResponse> getRoute(UUID fromHubId,UUID toHubId);
}
