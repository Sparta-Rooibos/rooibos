package com.sparta.rooibus.delivery.application.service.feign;

import com.sparta.rooibus.delivery.application.dto.response.feign.route.GetRouteResponse;
import com.sparta.rooibus.delivery.application.dto.request.feign.route.GetRouteRequest;
import org.springframework.stereotype.Component;

@Component
public interface RouteService {

    GetRouteResponse getRoute(GetRouteRequest request);
}
