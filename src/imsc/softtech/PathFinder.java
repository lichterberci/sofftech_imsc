package imsc.softtech;

/**
 * Find the shortest path in a weighted directed graph.
 */
interface PathFinder {
    /**
     * Adds an edge to the graph.
     *
     * @param from   the ID of the node at the start of the edge.
     * @param to     the ID of the node at the end of the edge.
     * @param weight the weight of the edge, either 1 or 0.
     * @throws IllegalArgumentException if the edge weight is invalid.
     * @throws IllegalStateException if the edge was already present in the graph.
     */
    void addEdge(int from, int to, int weight);

    /**
     * Calculates the weight of the shortest path in the graph.
     *
     * @param source the ID of the source node of the path.
     * @param target the ID of the target node of the path.
     * @returns the weight of the shortest path or -1 if there is no such path.
     */
    int getShortestPathLength(int source, int target);
}

