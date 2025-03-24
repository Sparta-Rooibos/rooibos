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
import sparta.rooibos.route.application.exception.BusinessRouteException;
import sparta.rooibos.route.application.exception.custom.RouteErrorCode;
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

        HubClientResponse fromHub = getHubByHubId(fromHubId);
        HubClientResponse toHub = getHubByHubId(toHubId);

        Route newRoute = Route.of(fromHubId, toHubId, fromHub.name(), toHub.name());

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
                getOptimizedRouteRequest.toHubId(),
                getOptimizedRouteRequest.priorityType(),
                getAllRoutesForServer()
        );

        List<GetOptimizedRouteResponse.RouteInfo> routeInfos = result.getPath().stream()
                .map(this::getRouteForServer)
                .map(route -> GetOptimizedRouteResponse.RouteInfo.of(
                        route.getFromHubId(),
                        route.getFromHubName(),
                        route.getToHubId(),
                        route.getToHubName(),
                        route.getDistance(),
                        route.getTimeCost()
                ))
                .toList();

        return GetOptimizedRouteResponse.from(result, routeInfos);
    }

    @Override
    @Transactional
    public UpdateRouteResponse updateRoute(UUID routeId, UpdateRouteRequest updateRouteRequest) {
        Route targetRoute = getRouteForServer(routeId);

        UUID fromHubId = updateRouteRequest.fromHubId();
        UUID toHubId = updateRouteRequest.toHubId();

        HubClientResponse fromHub = getHubByHubId(fromHubId);
        HubClientResponse toHub = getHubByHubId(toHubId);

        Route sourceRoute = Route.of(fromHubId, toHubId, fromHub.name(), toHub.name());
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

    @Override
    @Transactional
    public void deleteRouteByHubDeletedActivity(UUID hubId) {
        routeRepository.getAllRoutesByHubId(hubId).forEach(Route::delete);
    }

    /**
     * 서버에서만 사용하는 단건 및 전체 조회 메서드
     */
    private Route getRouteForServer(UUID routeId) {
        return routeRepository.getRoute(routeId)
                .orElseThrow(() -> new BusinessRouteException(RouteErrorCode.ROUTE_NOT_FOUND));
    }

    private List<Route> getAllRoutesForServer() {
        return routeRepository.getAllRoutes();
    }
}
