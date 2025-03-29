package com.sparta.rooibos.route.application.port.in;

import com.sparta.rooibos.route.application.dto.request.route.CreateRouteRequest;
import com.sparta.rooibos.route.application.dto.request.route.GetOptimizedRouteRequest;
import com.sparta.rooibos.route.application.dto.request.route.SearchRouteRequest;
import com.sparta.rooibos.route.application.dto.request.route.UpdateRouteRequest;
import com.sparta.rooibos.route.application.dto.response.route.*;

import java.util.UUID;

public interface RouteService {

    CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest);

    GetRouteResponse getRoute(UUID routeId);

    SearchRouteResponse searchRoute(String email, SearchRouteRequest searchRouteRequest);

    GetOptimizedRouteResponse getOptimizedRoute(GetOptimizedRouteRequest getOptimizedRouteRequest);

    UpdateRouteResponse updateRoute(UUID routeId, UpdateRouteRequest updateRouteRequest);

    void deleteRoute(UUID routeId);

    void deleteRouteByHubDeletedActivity(UUID hubId);
}
