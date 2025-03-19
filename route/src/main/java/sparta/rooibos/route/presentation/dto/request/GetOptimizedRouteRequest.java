package sparta.rooibos.route.presentation.dto.request;

import sparta.rooibos.route.service.DijkstraAlgorithm;

import java.util.UUID;

public record GetOptimizedRouteRequest(
        UUID fromHubId,
        UUID toHubID,
        DijkstraAlgorithm.PriorityType priorityType
) {}
