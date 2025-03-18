package sparta.rooibos.route.service;

import sparta.rooibos.route.presentation.dto.request.CreateRouteRequest;
import sparta.rooibos.route.presentation.dto.response.CreateRouteResponse;
import sparta.rooibos.route.presentation.dto.response.GetRouteResponse;

import java.util.UUID;

public interface RouteService {

    CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest);

    GetRouteResponse getRoute(UUID routeId);
}
