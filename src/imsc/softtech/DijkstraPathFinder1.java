package imsc.softtech;

import java.util.*;

public class DijkstraPathFinder1 implements PathFinder {
    private final Map<Integer, Map<Integer, Integer>> graph;

    public DijkstraPathFinder1() {
        this.graph = new HashMap<>();
    }

    @Override
    public void addEdge(int from, int to, int weight) {
        if (weight != 0 && weight != 1) {
            throw new IllegalArgumentException("Edge weight must be either 0 or 1.");
        }

        graph.computeIfAbsent(from, k -> new HashMap<>());
        if (graph.get(from).containsKey(to)) {
            throw new IllegalStateException("Edge already present in the graph.");
        }

        graph.get(from).put(to, weight);
    }

    @Override
    public int getShortestPathLength(int source, int target) {
        Map<Integer, Integer> distance = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distance::get));

        distance.put(source, 0);
        priorityQueue.add(new Node(source, 0));

        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll().vertex;

            if (current == target) {
                return distance.get(current);
            }

            if (graph.containsKey(current)) {
                for (int neighbor : graph.get(current).keySet()) {
                    int newDistance = distance.get(current) + graph.get(current).get(neighbor);

                    if (!distance.containsKey(neighbor) || newDistance < distance.get(neighbor)) {
                        distance.put(neighbor, newDistance);
                        priorityQueue.add(new Node(neighbor, newDistance));
                    }
                }
            }
        }

        return -1; // No path found
    }

    private static class Node {
        private final int vertex;
        private final int distance;

        public Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}
