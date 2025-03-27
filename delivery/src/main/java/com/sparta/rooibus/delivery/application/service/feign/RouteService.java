package com.sparta.rooibus.delivery.application.service.feign;

import com.sparta.rooibus.delivery.application.dto.request.feign.route.GetRouteRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.route.GetRouteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface RouteService {

    ResponseEntity<GetRouteResponse> getRoute(GetRouteRequest request);
}
