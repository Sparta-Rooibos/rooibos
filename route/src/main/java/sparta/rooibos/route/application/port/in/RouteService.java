package sparta.rooibos.route.application.port.in;

import sparta.rooibos.route.application.dto.request.CreateRouteRequest;
import sparta.rooibos.route.application.dto.request.GetOptimizedRouteRequest;
import sparta.rooibos.route.application.dto.request.SearchRouteRequest;
import sparta.rooibos.route.application.dto.request.UpdateRouteRequest;
import sparta.rooibos.route.application.dto.response.*;

import java.util.UUID;

public interface RouteService {

    CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest);

    GetRouteResponse getRoute(UUID routeId);

    SearchRouteResponse searchRoute(SearchRouteRequest searchRouteRequest);

    GetOptimizedRouteResponse getOptimizedRoute(GetOptimizedRouteRequest getOptimizedRouteRequest);

    UpdateRouteResponse updateRoute(UUID routeId, UpdateRouteRequest updateRouteRequest);

    void deleteRoute(UUID routeId);
}
