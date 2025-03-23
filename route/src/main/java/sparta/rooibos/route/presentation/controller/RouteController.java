package sparta.rooibos.route.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.rooibos.route.presentation.dto.request.CreateRouteRequest;
import sparta.rooibos.route.presentation.dto.request.GetOptimizedRouteRequest;
import sparta.rooibos.route.presentation.dto.request.SearchRouteRequest;
import sparta.rooibos.route.presentation.dto.request.UpdateRouteRequest;
import sparta.rooibos.route.presentation.dto.response.*;
import sparta.rooibos.route.service.port.in.RouteService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/route")
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<CreateRouteResponse> createRoute(@RequestBody CreateRouteRequest createRouteRequest) {
        return ResponseEntity.ok(routeService.createRoute(createRouteRequest));
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<GetRouteResponse> getRoute(@PathVariable UUID routeId) {
        return ResponseEntity.ok(routeService.getRoute(routeId));
    }

    @GetMapping
    public ResponseEntity<SearchRouteResponse> searchRoute(@ModelAttribute SearchRouteRequest searchRouteRequest) {
        return ResponseEntity.ok(routeService.searchRoute(searchRouteRequest));
    }

    @GetMapping("/recommend")
    public ResponseEntity<GetOptimizedRouteResponse> recommendRoute(
            @ModelAttribute GetOptimizedRouteRequest getOptimizedRouteRequest
    ) {
        return ResponseEntity.ok(routeService.getOptimizedRoute(getOptimizedRouteRequest));
    }

    @PatchMapping("/{routeId}")
    public ResponseEntity<UpdateRouteResponse> updateRoute(
            @PathVariable UUID routeId, @RequestBody UpdateRouteRequest updateRouteRequest
    ) {
        return ResponseEntity.ok(routeService.updateRoute(routeId, updateRouteRequest));
    }

    @PatchMapping("/{routeId}/delete")
    public ResponseEntity<Void> deleteRoute(@PathVariable UUID routeId) {
        routeService.deleteRoute(routeId);

        return ResponseEntity.noContent().build();
    }
}
