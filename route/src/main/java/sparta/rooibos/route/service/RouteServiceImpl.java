package sparta.rooibos.route.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.route.domain.model.Route;
import sparta.rooibos.route.domain.repository.RouteRepository;
import sparta.rooibos.route.presentation.dto.request.CreateRouteRequest;
import sparta.rooibos.route.presentation.dto.request.UpdateRouteRequest;
import sparta.rooibos.route.presentation.dto.response.CreateRouteResponse;
import sparta.rooibos.route.presentation.dto.response.GetRouteResponse;
import sparta.rooibos.route.presentation.dto.response.UpdateRouteResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

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

    /**
     * 서버에서만 사용하는 단건 조회 메서드
     */
    // TODO 도메인 커스텀 예외로 전환
    private Route getRouteForServer(UUID routeId) {
        return routeRepository.getRoute(routeId)
                .orElseThrow(() -> new IllegalArgumentException("Route not found"));
    }
}
