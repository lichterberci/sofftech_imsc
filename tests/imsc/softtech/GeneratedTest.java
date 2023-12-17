package imsc.softtech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeneratedTest {

    private PathFinder pathFinder;

    @BeforeEach
    void setUp() {
        // Initialize the PathFinder implementation before each test
        pathFinder = new DijkstraPathFinder3();  // Replace with the actual implementation class
    }

    /**
     * This checks that we can add an edge (addEdge happy path)
     */
    @Test
    void addEdge_ValidInput_AddsEdgeSuccessfully() {
        pathFinder.addEdge(1, 2, 1);
        // Add assertions to verify that the edge was added successfully
        // You might want to use additional methods to check the state of the graph
    }


    /**
     * This checks that we do not accept invalid weights
     */
    @Test
    void addEdge_InvalidWeight_ThrowsIllegalArgumentException() {
        // Add assertions to check that IllegalArgumentException is thrown for invalid weight

        // EDITOR: this is a good test value, but I would add some more
        assertThrows(IllegalArgumentException.class, () -> pathFinder.addEdge(1, 2, 2));
    }

    /**
     * This checks that duplicate edges are not allowed
     */
    @Test
    void addEdge_DuplicateEdge_ThrowsIllegalStateException() {
        // Add assertions to check that IllegalStateException is thrown for a duplicate edge
        pathFinder.addEdge(1, 2, 1);
        // EDITOR: this is a good assertion
        assertThrows(IllegalStateException.class, () -> pathFinder.addEdge(1, 2, 1));
    }

    /**
     * This is a pathfinding happy path
     */
    @Test
    void getShortestPathLength_ExistingPath_ReturnsCorrectLength() {
        // Add edges to the graph to create a known path
        pathFinder.addEdge(1, 2, 1);
        pathFinder.addEdge(2, 3, 1);
        int shortestPathLength = pathFinder.getShortestPathLength(1, 3);
        // EDITOR: correct assertion
        assertEquals(2, shortestPathLength);
    }

    /**
     * This checks for the case of no path existing between the source and the target
     */
    @Test
    void getShortestPathLength_NoPath_ReturnsMinusOne() {
        // Add assertions to check that -1 is returned when there is no path
        int shortestPathLength = pathFinder.getShortestPathLength(1, 4);
        // EDITOR: correct assertion
        assertEquals(-1, shortestPathLength);
    }
}
