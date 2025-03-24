package sparta.rooibos.route.application.dto.request.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import sparta.rooibos.route.application.core.DijkstraAlgorithm;

import java.util.UUID;

public record GetOptimizedRouteRequest(
        @JsonProperty("fromHubId") UUID fromHubId,
        @JsonProperty("toHubId") UUID toHubId,
        @JsonProperty("priorityType") DijkstraAlgorithm.PriorityType priorityType
) {}
