package sparta.rooibos.route.service;

import sparta.rooibos.route.presentation.dto.request.CreateRouteRequest;
import sparta.rooibos.route.presentation.dto.response.CreateRouteResponse;

public interface RouteService {

    CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest);
}
