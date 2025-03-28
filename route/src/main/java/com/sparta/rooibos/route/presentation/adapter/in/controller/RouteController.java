package com.sparta.rooibos.route.presentation.adapter.in.controller;

import com.sparta.rooibos.route.application.aop.RoleCheck;
import com.sparta.rooibos.route.application.dto.request.route.CreateRouteRequest;
import com.sparta.rooibos.route.application.dto.request.route.GetOptimizedRouteRequest;
import com.sparta.rooibos.route.application.dto.request.route.SearchRouteRequest;
import com.sparta.rooibos.route.application.dto.request.route.UpdateRouteRequest;
import com.sparta.rooibos.route.application.dto.response.route.*;
import com.sparta.rooibos.route.application.port.in.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/route")
public class RouteController {

    private final RouteService routeService;

    @RoleCheck({"MASTER"})
    @PostMapping
    public ResponseEntity<CreateRouteResponse> createRoute(@RequestBody CreateRouteRequest createRouteRequest) {
        return ResponseEntity.ok(routeService.createRoute(createRouteRequest));
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<GetRouteResponse> getRoute(@PathVariable("routeId") UUID routeId) {
        return ResponseEntity.ok(routeService.getRoute(routeId));
    }

    @GetMapping
    public ResponseEntity<SearchRouteResponse> searchRoute(
            @RequestHeader("X-User-Email") String email,
            @ModelAttribute SearchRouteRequest searchRouteRequest
    ) {
        return ResponseEntity.ok(routeService.searchRoute(email, searchRouteRequest));
    }

    @GetMapping("/recommend")
    public ResponseEntity<GetOptimizedRouteResponse> recommendRoute(
            @RequestBody GetOptimizedRouteRequest getOptimizedRouteRequest
    ) {
        return ResponseEntity.ok(routeService.getOptimizedRoute(getOptimizedRouteRequest));
    }

    @RoleCheck({"MASTER"})
    @PatchMapping("/{routeId}")
    public ResponseEntity<UpdateRouteResponse> updateRoute(
            @PathVariable("routeId") UUID routeId,
            @RequestBody UpdateRouteRequest updateRouteRequest
    ) {
        return ResponseEntity.ok(routeService.updateRoute(routeId, updateRouteRequest));
    }

    @RoleCheck({"MASTER"})
    @PatchMapping("/{routeId}/delete")
    public ResponseEntity<Void> deleteRoute(@PathVariable("routeId") UUID routeId) {
        routeService.deleteRoute(routeId);

        return ResponseEntity.noContent().build();
    }
}
