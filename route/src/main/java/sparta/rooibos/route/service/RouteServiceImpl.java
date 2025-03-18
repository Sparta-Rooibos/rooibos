package sparta.rooibos.route.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.rooibos.route.domain.model.Route;
import sparta.rooibos.route.domain.repository.RouteRepository;
import sparta.rooibos.route.presentation.dto.request.CreateRouteRequest;
import sparta.rooibos.route.presentation.dto.response.CreateRouteResponse;

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
}
