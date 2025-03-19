package sparta.rooibos.route.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import sparta.rooibos.route.presentation.dto.request.GetOptimizedRouteRequest;
import sparta.rooibos.route.presentation.dto.response.GetOptimizedRouteResponse;

@FeignClient(name = "route-service")
public interface RouteFeignClient {

    @GetMapping("/api/v1/route/recommend")
    GetOptimizedRouteResponse getOptimizedRoute(@ModelAttribute GetOptimizedRouteRequest getOptimizedRouteRequest);
}
