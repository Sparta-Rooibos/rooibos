package sparta.rooibos.route.application.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sparta.rooibos.route.domain.model.Route;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DijkstraAlgorithm {

    @Getter
    @RequiredArgsConstructor
    public static class Node implements Comparable<Node> {
        private final UUID hubId;
        private final int totalCost;

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.totalCost, o.totalCost);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class Edge {
        private final UUID routeId;
        private final UUID fromHubId;
        private final UUID toHubId;
        private final Integer distance;
        private final Integer time;
    }

    public enum PriorityType {
        DISTANCE, TIME
    }

    @Getter
    @RequiredArgsConstructor
    public static class Result {
        private final PriorityType priorityType;
        private final List<UUID> path;

        private static Result of(PriorityType priorityType, List<UUID> path) {
            return new Result(priorityType, path);
        }
    }

    public Result getOptimizedRoutes(
            UUID fromHubId,
            UUID toHubId,
            PriorityType priorityType,
            List<Route> routes) {

        Map<UUID, Integer> cost = new HashMap<>();
        Map<UUID, Edge> predecessorEdge = new HashMap<>();
        Map<UUID, List<Edge>> adjacencyList = initAdjacencyList(routes);

        Set<UUID> allHubs = new HashSet<>(adjacencyList.keySet());
        for (List<Edge> edges : adjacencyList.values()) {
            for (Edge edge : edges) {
                allHubs.add(edge.getToHubId());
            }
        }

        for (UUID hubId : allHubs) {
            cost.put(hubId, Integer.MAX_VALUE);
            predecessorEdge.put(hubId, null);
        }

        cost.put(fromHubId, 0);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(fromHubId, 0));

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            UUID currentHubId = currentNode.getHubId();
            int currentCost = currentNode.getTotalCost();

            if (currentCost > cost.get(currentHubId)) {
                continue;
            }

            List<Edge> edges = adjacencyList.getOrDefault(currentHubId, Collections.emptyList());
            for (Edge edge : edges) {
                UUID nextHubId = edge.getToHubId();
                int weight = (priorityType == PriorityType.DISTANCE) ? edge.getDistance() : edge.getTime();
                int nextCost = currentCost + weight;

                if (nextCost < cost.get(nextHubId)) {
                    cost.put(nextHubId, nextCost);
                    predecessorEdge.put(nextHubId, edge);
                    pq.offer(new Node(nextHubId, nextCost));
                }
            }
        }

        if (cost.get(toHubId) == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("No path found");
        }

        List<UUID> routeIdPath = new ArrayList<>();
        UUID currentHub = toHubId;
        while (!currentHub.equals(fromHubId)) {
            Edge edge = predecessorEdge.get(currentHub);
            if (edge == null) {
                throw new IllegalStateException("Path reconstruction failed");
            }
            routeIdPath.add(edge.getRouteId());
            currentHub = edge.getFromHubId();
        }
        Collections.reverse(routeIdPath);

        return Result.of(priorityType, routeIdPath);
    }

    private Map<UUID, List<Edge>> initAdjacencyList(List<Route> routes) {
        return routes.stream()
                .collect(Collectors.groupingBy(
                        Route::getFromHubId,
                        Collectors.mapping(
                                r -> new Edge(
                                        r.getRouteId(),
                                        r.getFromHubId(),
                                        r.getToHubId(),
                                        r.getDistance(),
                                        r.getTimeCost()
                                ),
                                Collectors.toList()
                        )
                ));
    }
}