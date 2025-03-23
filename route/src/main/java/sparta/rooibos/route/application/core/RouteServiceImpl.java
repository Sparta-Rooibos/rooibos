package sparta.rooibos.route.application.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.route.application.dto.request.route.CreateRouteRequest;
import sparta.rooibos.route.application.dto.request.route.GetOptimizedRouteRequest;
import sparta.rooibos.route.application.dto.request.route.SearchRouteRequest;
import sparta.rooibos.route.application.dto.request.route.UpdateRouteRequest;
import sparta.rooibos.route.application.dto.response.direction.GetGeoDirectionResponse;
import sparta.rooibos.route.application.dto.response.hub.HubClientResponse;
import sparta.rooibos.route.application.dto.response.route.*;
import sparta.rooibos.route.application.port.in.RouteService;
import sparta.rooibos.route.application.port.out.GeoDirectionService;
import sparta.rooibos.route.application.port.out.HubClientService;
import sparta.rooibos.route.domain.model.Route;
import sparta.rooibos.route.domain.repository.RouteRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final DijkstraAlgorithm dijkstraAlgorithm;
    private final HubClientService hubClientService;
    private final GeoDirectionService geoDirectionService;

    @Override
    @Transactional
    public CreateRouteResponse createRoute(CreateRouteRequest createRouteRequest) {
        UUID fromHubId = createRouteRequest.fromHubId();
        UUID toHubId = createRouteRequest.toHubId();
        Route newRoute = Route.of(fromHubId, toHubId);

        GetGeoDirectionResponse result = getDirectionResult(getCoordinates(fromHubId), getCoordinates(toHubId));
        newRoute.setDistanceAndDuration(result.getDistance(), result.getDuration());

        Route savedRoute = routeRepository.createRoute(newRoute);
        return CreateRouteResponse.from(savedRoute);
    }

    private String getCoordinates(UUID hubId) {
        return getHubByHubId(hubId).getCoordinates();
    }

    private HubClientResponse getHubByHubId(UUID hubId) {
        return hubClientService.getHubByHubId(hubId);
    }

    private GetGeoDirectionResponse getDirectionResult(String fromCoordinates, String toCoordinates) {
        return geoDirectionService.getGeoDirection(fromCoordinates, toCoordinates);
    }

    @Override
    public GetRouteResponse getRoute(UUID routeId) {
        return GetRouteResponse.from(getRouteForServer(routeId));
    }

//    @Cacheable(
//            cacheNames = "searchRoute",
//            key = "'searchRoute' + (#searchRouteRequest.sort()) + ':' + (#searchRouteRequest.size())"
//    )
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

//    @Cacheable(
//            cacheNames = "gerOptimizedRoute",
//            key = "'getOptimizedRoute' + (#getOptimizedRouteRequest.priorityType())"
//    )
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

        UUID fromHubId = updateRouteRequest.fromHubId();
        UUID toHubId = updateRouteRequest.toHubId();
        Route sourceRoute = Route.of(fromHubId, toHubId);
        Route updatedRoute = targetRoute.update(sourceRoute);

        GetGeoDirectionResponse result = getDirectionResult(getCoordinates(fromHubId), getCoordinates(toHubId));
        updatedRoute.setDistanceAndDuration(result.getDistance(), result.getDuration());
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
