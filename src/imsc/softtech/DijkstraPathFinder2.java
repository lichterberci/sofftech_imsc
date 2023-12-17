package imsc.softtech;
import java.util.*;

public class DijkstraPathFinder2 implements PathFinder {
    private Map<Integer, Map<Integer, Integer>> graph;

    public DijkstraPathFinder2() {
        this.graph = new HashMap<>();
    }

    @Override
    public void addEdge(int from, int to, int weight) {
        if (weight != 0 && weight != 1) {
            throw new IllegalArgumentException("Invalid edge weight. It should be either 0 or 1.");
        }

        graph.computeIfAbsent(from, k -> new HashMap<>()).put(to, weight);
        graph.putIfAbsent(to, new HashMap<>()); // Ensure the 'to' node exists in the graph
        graph.putIfAbsent(from, new HashMap<>()); // Ensure the 'from' node exists in the graph
    }

    @Override
    public int getShortestPathLength(int source, int target) {
        if (!graph.containsKey(source) || !graph.containsKey(target)) {
            return -1; // Source or target node not present in the graph
        }

        Map<Integer, Integer> distances = new HashMap<>();
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<Integer> visited = new HashSet<>();

        minHeap.add(new Node(source, 0));
        distances.put(source, 0);

        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.poll();
            int current = currentNode.id;

            if (current == target) {
                return distances.get(current);
            }

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            for (Map.Entry<Integer, Integer> neighborEntry : graph.getOrDefault(current, Collections.emptyMap()).entrySet()) {
                int neighbor = neighborEntry.getKey();
                int newDistance = distances.getOrDefault(current, 0) + neighborEntry.getValue();

                if (!distances.containsKey(neighbor) || newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    minHeap.add(new Node(neighbor, newDistance));
                }
            }
        }

        return -1; // No path found
    }

    private static class Node {
        private final int id;
        private final int distance;

        private Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }
    }
}
