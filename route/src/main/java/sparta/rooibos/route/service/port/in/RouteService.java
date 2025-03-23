package sparta.rooibos.route.service.port.in;

import sparta.rooibos.route.presentation.dto.request.CreateRouteRequest;
import sparta.rooibos.route.presentation.dto.request.GetOptimizedRouteRequest;
import sparta.rooibos.route.presentation.dto.request.SearchRouteRequest;
import sparta.rooibos.route.presentation.dto.request.UpdateRouteRequest;
import sparta.rooibos.route.presentation.dto.response.*;

import java.util.UUID;

public interface RouteService {

    CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest);

    GetRouteResponse getRoute(UUID routeId);

    SearchRouteResponse searchRoute(SearchRouteRequest searchRouteRequest);

    GetOptimizedRouteResponse getOptimizedRoute(GetOptimizedRouteRequest getOptimizedRouteRequest);

    UpdateRouteResponse updateRoute(UUID routeId, UpdateRouteRequest updateRouteRequest);

    void deleteRoute(UUID routeId);
}
