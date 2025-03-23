package sparta.rooibos.route.presentation.dto.request;

import sparta.rooibos.route.service.core.DijkstraAlgorithm;

import java.util.UUID;

public record GetOptimizedRouteRequest(
        UUID fromHubId,
        UUID toHubID,
        DijkstraAlgorithm.PriorityType priorityType
) {}
