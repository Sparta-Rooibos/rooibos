package sparta.rooibos.route.application.dto.request.route;

import sparta.rooibos.route.application.core.DijkstraAlgorithm;

import java.util.UUID;

public record GetOptimizedRouteRequest(
        UUID fromHubId,
        UUID toHubID,
        DijkstraAlgorithm.PriorityType priorityType
) {}
