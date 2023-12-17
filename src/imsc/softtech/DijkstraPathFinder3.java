package imsc.softtech;

import java.util.*;

class DijkstraPathFinder3 implements PathFinder {

    private final Map<Integer, Map<Integer, Integer>> graph;

    public DijkstraPathFinder3() {
        this.graph = new HashMap<>();
    }

    @Override
    public void addEdge(int from, int to, int weight) {
        if (weight != 0 && weight != 1) {
            throw new IllegalArgumentException("Edge weight must be either 0 or 1");
        }

        if (graph.computeIfAbsent(from, k -> new HashMap<>()).put(to, weight) != null) {
            throw new IllegalStateException("Edge already present in the graph");
        }
    }

    @Override
    public int getShortestPathLength(int source, int target) {
        Map<Integer, Integer> distances = new HashMap<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        minHeap.add(new int[]{source, 0});
        distances.put(source, 0);

        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int currentNode = current[0];
            int currentDistance = current[1];

            if (currentNode == target) {
                return currentDistance;
            }

            if (!graph.containsKey(currentNode)) {
                continue;
            }

            for (Map.Entry<Integer, Integer> neighbor : graph.get(currentNode).entrySet()) {
                int neighborNode = neighbor.getKey();
                int newDistance = currentDistance + neighbor.getValue();

                if (!distances.containsKey(neighborNode) || newDistance < distances.get(neighborNode)) {
                    distances.put(neighborNode, newDistance);
                    minHeap.add(new int[]{neighborNode, newDistance});
                }
            }
        }

        return -1; // No path found
    }

    public static void main(String[] args) {
        DijkstraPathFinder3 pathFinder = new DijkstraPathFinder3();

        pathFinder.addEdge(0, 1, 1);
        pathFinder.addEdge(1, 2, 1);
        pathFinder.addEdge(0, 2, 0);

        int shortestPathLength = pathFinder.getShortestPathLength(0, 2);
        System.out.println("Shortest path length: " + shortestPathLength);
    }
}
