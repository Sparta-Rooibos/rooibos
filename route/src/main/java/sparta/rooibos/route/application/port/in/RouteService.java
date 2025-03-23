package sparta.rooibos.route.application.port.in;

import sparta.rooibos.route.application.dto.request.route.CreateRouteRequest;
import sparta.rooibos.route.application.dto.request.route.GetOptimizedRouteRequest;
import sparta.rooibos.route.application.dto.request.route.SearchRouteRequest;
import sparta.rooibos.route.application.dto.request.route.UpdateRouteRequest;
import sparta.rooibos.route.application.dto.response.route.*;

import java.util.UUID;

public interface RouteService {

    CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest);

    GetRouteResponse getRoute(UUID routeId);

    SearchRouteResponse searchRoute(SearchRouteRequest searchRouteRequest);

    GetOptimizedRouteResponse getOptimizedRoute(GetOptimizedRouteRequest getOptimizedRouteRequest);

    UpdateRouteResponse updateRoute(UUID routeId, UpdateRouteRequest updateRouteRequest);

    void deleteRoute(UUID routeId);
}
