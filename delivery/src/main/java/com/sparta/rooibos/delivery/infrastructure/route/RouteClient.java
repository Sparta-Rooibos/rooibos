package com.sparta.rooibos.delivery.infrastructure.route;


import com.sparta.rooibos.delivery.application.dto.request.feign.route.GetRouteRequest;
import com.sparta.rooibos.delivery.application.dto.response.feign.route.GetRouteResponse;
import com.sparta.rooibos.delivery.application.service.feign.RouteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Primary
@FeignClient(name = "route-service", url = "http://localhost:19100")
public interface RouteClient extends RouteService {
    @GetMapping("/api/v1/route/recommend")
    ResponseEntity<GetRouteResponse> getRoute(@RequestBody GetRouteRequest request);
}
