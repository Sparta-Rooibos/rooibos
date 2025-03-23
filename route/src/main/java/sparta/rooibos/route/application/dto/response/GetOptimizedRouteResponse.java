package sparta.rooibos.route.application.dto.response;

import sparta.rooibos.route.application.core.DijkstraAlgorithm;

public record GetOptimizedRouteResponse(
        DijkstraAlgorithm.Result result
) {
    public static GetOptimizedRouteResponse from(DijkstraAlgorithm.Result result) {
        return new GetOptimizedRouteResponse(result);
    }
}
