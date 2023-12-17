package imsc.softtech;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    private PathFinder pathFinder;

    @BeforeEach
    void beforeEach() {
        pathFinder = new DijkstraPathFinder3();
    }

    @Test
    void singleEdgeTest() {
        pathFinder.addEdge(0, 1, 1);
        assertEquals(1, pathFinder.getShortestPathLength(0, 1));
    }

    @Test
    void noPathTest () {
        pathFinder.addEdge(0, 1, 1);
        pathFinder.addEdge(2, 3, 1);
        assertEquals(-1, pathFinder.getShortestPathLength(0, 3));
    }

    @Test
    void emptyGraphTest () {
        assertEquals(-1, pathFinder.getShortestPathLength(0, 1));
    }

    @Test
    void weightedDiamond () {
        pathFinder.addEdge(0, 1, 1);
        pathFinder.addEdge(1, 2, 1);
        pathFinder.addEdge(0, 3, 1);
        pathFinder.addEdge(2, 3, 0);
        assertEquals(1, pathFinder.getShortestPathLength(0, 3));
    }

    @Test
    void testThatChainedZeroWeightedEdgesArePreferredOverOneWeightedDirectEdge () {
        pathFinder.addEdge(0, 1, 0);
        pathFinder.addEdge(1, 2, 0);
        pathFinder.addEdge(2, 3, 0);
        pathFinder.addEdge(3, 4, 0);
        pathFinder.addEdge(0, 4, 1);
        assertEquals(0, pathFinder.getShortestPathLength(0, 4));
    }

    @Test
    void testThatDefiningMultipleEdgesDoesThrow () {
        pathFinder.addEdge(0, 1, 1);
        assertThrows(IllegalStateException.class, () -> pathFinder.addEdge(0, 1, 1));
    }

    @Test
    void testThatOnlyValidEdgesWithValidWeightsAreAccepted () {
        assertDoesNotThrow(() -> pathFinder.addEdge(0, 1, 0));
        assertDoesNotThrow(() -> pathFinder.addEdge(0, 2, 1));
        assertThrows(IllegalArgumentException.class, () -> pathFinder.addEdge(0, 3, -1));
        assertThrows(IllegalArgumentException.class, () -> pathFinder.addEdge(0, 4, -2));
        assertThrows(IllegalArgumentException.class, () -> pathFinder.addEdge(0, 5, 2));
    }

    @Test
    void testDirectionality () {
        pathFinder.addEdge(0, 1, 1);
        pathFinder.addEdge(2, 1, 1);
        assertEquals(-1, pathFinder.getShortestPathLength(0, 2));
    }

    @Test
    void testThatWeightIsZeroWhenStartAndEndNodesAreTheSame () {
        pathFinder.addEdge(0, 1, 1);
        pathFinder.addEdge(1, 0, 1);
        assertEquals(0, pathFinder.getShortestPathLength(0, 0));
    }

    @Test
    void testThatItHandlesLoops () {
        pathFinder.addEdge(0, 0, 1);
        pathFinder.addEdge(0, 1, 1);
        pathFinder.addEdge(1, 2, 1);
        pathFinder.addEdge(2, 2, 1);
        assertEquals(2, pathFinder.getShortestPathLength(0, 2));
    }

    @Test
    void complexGraph () {

        /*
        * Graph: (~transit graph)
        *
        * 0 = 1 = 2 = 3 = 4
        *    |0|     /\   \/
        *     10      5 = 6
        *     ||      \\
        *     LL= 20 = 30
        *              |0|
        *              300 = 400 = 500
        * */

        pathFinder.addEdge(0, 1, 1);
        pathFinder.addEdge(1, 0, 1);

        pathFinder.addEdge(1, 2, 1);
        pathFinder.addEdge(2, 1, 1);

        pathFinder.addEdge(2, 3, 1);
        pathFinder.addEdge(3, 2, 1);

        pathFinder.addEdge(3, 4, 1);
        pathFinder.addEdge(5, 3, 1);

        pathFinder.addEdge(4, 6, 1);
        pathFinder.addEdge(6, 5, 1);

        pathFinder.addEdge(1, 10, 0);
        pathFinder.addEdge(10, 1, 0);

        pathFinder.addEdge(10, 20, 1);
        pathFinder.addEdge(20, 10, 1);

        pathFinder.addEdge(20, 30, 1);
        pathFinder.addEdge(30, 20, 1);

        pathFinder.addEdge(30, 5, 1);
        pathFinder.addEdge(5, 30, 1);

        pathFinder.addEdge(30, 300, 0);
        pathFinder.addEdge(300, 30, 0);

        pathFinder.addEdge(300, 400, 1);
        pathFinder.addEdge(400, 300, 1);

        pathFinder.addEdge(400, 500, 1);
        pathFinder.addEdge(500, 400, 1);

        assertEquals(1, pathFinder.getShortestPathLength(0, 10));
        assertEquals(4, pathFinder.getShortestPathLength(0, 4));
        assertEquals(3, pathFinder.getShortestPathLength(400, 3));
        assertEquals(5, pathFinder.getShortestPathLength(3, 400));
        assertEquals(3, pathFinder.getShortestPathLength(3, 5));
        assertEquals(1, pathFinder.getShortestPathLength(5, 3));
        assertEquals(0, pathFinder.getShortestPathLength(30, 300));
        assertEquals(5, pathFinder.getShortestPathLength(500, 4));
    }
}