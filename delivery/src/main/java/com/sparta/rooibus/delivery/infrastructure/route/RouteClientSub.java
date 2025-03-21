package com.sparta.rooibus.delivery.infrastructure.route;

import com.sparta.rooibus.delivery.application.dto.response.feign.route.GetRouteResponse;
import com.sparta.rooibus.delivery.application.dto.request.feign.route.GetRouteRequest;
import com.sparta.rooibus.delivery.application.service.feign.RouteService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class RouteClientSub implements RouteService {

    @Override
    public GetRouteResponse getRoute(GetRouteRequest request) {
        List<UUID> sampleRoute = List.of(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID()
        );

        return new GetRouteResponse(
            sampleRoute,
            "15km",
            "30분"
        );
    }
}
