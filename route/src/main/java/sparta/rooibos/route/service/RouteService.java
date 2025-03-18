package sparta.rooibos.route.service;

import sparta.rooibos.route.presentation.dto.request.CreateRouteRequest;
import sparta.rooibos.route.presentation.dto.request.SearchRouteRequest;
import sparta.rooibos.route.presentation.dto.request.UpdateRouteRequest;
import sparta.rooibos.route.presentation.dto.response.CreateRouteResponse;
import sparta.rooibos.route.presentation.dto.response.GetRouteResponse;
import sparta.rooibos.route.presentation.dto.response.SearchRouteResponse;
import sparta.rooibos.route.presentation.dto.response.UpdateRouteResponse;

import java.util.UUID;

public interface RouteService {

    CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest);

    GetRouteResponse getRoute(UUID routeId);

    SearchRouteResponse searchRoute(SearchRouteRequest searchRouteRequest);

    UpdateRouteResponse updateRoute(UUID routeId, UpdateRouteRequest updateRouteRequest);

    void deleteRoute(UUID routeId);
}
