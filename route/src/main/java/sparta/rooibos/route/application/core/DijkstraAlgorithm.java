package sparta.rooibos.route.application.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sparta.rooibos.route.domain.model.Route;

import java.util.*;
import java.util.stream.Collectors;

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
        private final int totalCost;

        private static Result of(PriorityType priorityType, List<UUID> path, int totalCost) {
            return new Result(priorityType, path, totalCost);
        }
    }

    public Result getOptimizedRoutes(
            UUID fromHubId,
            UUID toHubId,
            PriorityType priorityType,
            List<Route> routes) {

        Map<UUID, Integer> cost = new HashMap<>();
        Map<UUID, UUID> graph = new HashMap<>();
        Map<UUID, List<Edge>> adjacencyList = initAdjacencyList(routes);

        Set<UUID> allHubs = new HashSet<>(adjacencyList.keySet());
        for (List<Edge> edges : adjacencyList.values()) {
            for (Edge edge : edges) {
                allHubs.add(edge.toHubId);
            }
        }

        for (UUID hubId : allHubs) {
            cost.put(hubId, Integer.MAX_VALUE);
            graph.put(hubId, null);
        }

        cost.put(fromHubId, 0);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(fromHubId, 0));

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            UUID currentHubId = currentNode.hubId;
            int currentCost = currentNode.totalCost;

            if (currentCost > cost.get(currentHubId)) {
                continue;
            }

            List<Edge> edges = adjacencyList.getOrDefault(currentHubId, Collections.emptyList());
            for (Edge edge : edges) {
                UUID nextHubId = edge.toHubId;
                int weight = (priorityType == PriorityType.DISTANCE) ? edge.distance : edge.time;
                int nextCost = currentCost + weight;

                if (nextCost < cost.get(nextHubId)) {
                    cost.put(nextHubId, nextCost);
                    graph.put(nextHubId, currentHubId);

                    pq.offer(new Node(nextHubId, nextCost));
                }
            }
        }

        int finalCost = cost.get(toHubId);
        if (cost.get(toHubId) == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("No path found");
        }

        List<UUID> path = new ArrayList<>();
        UUID tracking = toHubId;
        while (tracking != null) {
            path.add(tracking);
            tracking = graph.get(tracking);
        }

        Collections.reverse(path);
        return Result.of(priorityType, path, finalCost);
    }

    private Map<UUID, List<Edge>> initAdjacencyList(List<Route> routes) {
        return routes.stream()
                .collect(Collectors.groupingBy(
                        Route::getFromHubId,
                        Collectors.mapping(
                                r -> new Edge(r.getFromHubId(), r.getToHubId(), r.getDistance(), r.getTimeCost()),
                                Collectors.toList()
                        )
                ));
    }
}
