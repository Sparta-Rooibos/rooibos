package sparta.rooibos.route.application.core;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.route.application.dto.response.route.*;
import sparta.rooibos.route.domain.model.Route;
import sparta.rooibos.route.domain.repository.RouteRepository;
import sparta.rooibos.route.application.dto.request.route.CreateRouteRequest;
import sparta.rooibos.route.application.dto.request.route.GetOptimizedRouteRequest;
import sparta.rooibos.route.application.dto.request.route.SearchRouteRequest;
import sparta.rooibos.route.application.dto.request.route.UpdateRouteRequest;
import sparta.rooibos.route.application.port.in.RouteService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final DijkstraAlgorithm dijkstraAlgorithm;

    @Override
    @Transactional
    public CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest) {
        Route newRoute = Route.of(
                createRouteRequest.fromHubId(),
                createRouteRequest.toHubId(),
                createRouteRequest.distance(),
                createRouteRequest.timeCost()
        );

        Route savedRoute = routeRepository.createRoute(newRoute);
        return CreateRouteResponse.from(savedRoute);
    }

    @Override
    public GetRouteResponse getRoute(UUID routeId) {
        return GetRouteResponse.from(getRouteForServer(routeId));
    }

    @Cacheable(
            cacheNames = "searchRoute",
            key = "'searchRoute' + (#searchRouteRequest.sort()) + ':' + (#searchRouteRequest.size())"
    )
    @Override
    public SearchRouteResponse searchRoute(SearchRouteRequest searchRouteRequest) {
        List<Route> routes = routeRepository.searchRoute(
                searchRouteRequest.fromHubId(),
                searchRouteRequest.toHubId(),
                searchRouteRequest.sort(),
                searchRouteRequest.size(),
                searchRouteRequest.lastCreatedAt(),
                searchRouteRequest.lastDistance(),
                searchRouteRequest.lastTotalCost()
        );

        return SearchRouteResponse.from(routes, searchRouteRequest.sort());
    }

    @Cacheable(
            cacheNames = "gerOptimizedRoute",
            key = "'getOptimizedRoute' + (#getOptimizedRouteRequest.priorityType())"
    )
    @Override
    public GetOptimizedRouteResponse getOptimizedRoute(GetOptimizedRouteRequest getOptimizedRouteRequest) {
        DijkstraAlgorithm.Result result = dijkstraAlgorithm.getOptimizedRoutes(
                getOptimizedRouteRequest.fromHubId(),
                getOptimizedRouteRequest.toHubID(),
                getOptimizedRouteRequest.priorityType(),
                getAllRoutesForServer()
        );

        return GetOptimizedRouteResponse.from(result);
    }

    @Override
    @Transactional
    public UpdateRouteResponse updateRoute(UUID routeId, UpdateRouteRequest updateRouteRequest) {
        Route targetRoute = getRouteForServer(routeId);
        Route sourceRoute = Route.of(
                updateRouteRequest.fromHubId(),
                updateRouteRequest.toHubId(),
                updateRouteRequest.distance(),
                updateRouteRequest.timeCost()
        );
        Route updatedRoute = targetRoute.update(sourceRoute);
        return UpdateRouteResponse.from(updatedRoute);
    }

    @Override
    @Transactional
    public void deleteRoute(UUID routeId) {
        Route targetRoute = getRouteForServer(routeId);
        targetRoute.delete();
    }

    /**
     * 서버에서만 사용하는 단건 및 전체 조회 메서드
     */
    // TODO 도메인 커스텀 예외로 전환
    private Route getRouteForServer(UUID routeId) {
        return routeRepository.getRoute(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Route not found"));
    }

    private List<Route> getAllRoutesForServer() {
        return routeRepository.getAllRoutes();
    }
}
