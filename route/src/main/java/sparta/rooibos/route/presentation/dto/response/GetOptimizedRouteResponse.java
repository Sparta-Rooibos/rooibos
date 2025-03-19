package sparta.rooibos.route.presentation.dto.response;

import sparta.rooibos.route.service.DijkstraAlgorithm;

public record GetOptimizedRouteResponse(
        DijkstraAlgorithm.Result result
) {
    public static GetOptimizedRouteResponse from(DijkstraAlgorithm.Result result) {
        return new GetOptimizedRouteResponse(result);
    }
}
