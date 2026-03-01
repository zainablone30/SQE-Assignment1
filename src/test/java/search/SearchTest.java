package search;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║    ALL-PATHS COVERAGE — All Three Search Algorithms     ║
 * ╠══════════════════════════════════════════════════════════╣
 * ║  1. BinarySearch   (sorted array)                       ║
 * ║  2. LinearSearch   (any array + String array)           ║
 * ║  3. BreadthFirstSearch (graph)                          ║
 * ║  4. OrderSearch    (integration of all 3)               ║
 * ╚══════════════════════════════════════════════════════════╝
 */
@DisplayName("Search Algorithms – All-Paths Test Suite")
class SearchTest {

    // ═══════════════════════════════════════════════════════
    // BINARY SEARCH
    // CFG Paths:
    //   P1: null or empty input   → -1
    //   P2: found at mid first try
    //   P3: target in left half   → high = mid - 1
    //   P4: target in right half  → low  = mid + 1
    //   P5: not found             → -1
    // ═══════════════════════════════════════════════════════

    @Test @DisplayName("BS P1a – null array → -1")
    void bs_Null() { assertEquals(-1, new BinarySearch().search(null, 5)); }

    @Test @DisplayName("BS P1b – empty array → -1")
    void bs_Empty() { assertEquals(-1, new BinarySearch().search(new int[]{}, 5)); }

    @Test @DisplayName("BS P2 – found at mid on first iteration")
    void bs_AtMid() { assertEquals(2, new BinarySearch().search(new int[]{1,3,5,7,9}, 5)); }

    @Test @DisplayName("BS P3 – target in left half")
    void bs_LeftHalf() { assertEquals(0, new BinarySearch().search(new int[]{1,3,5,7,9}, 1)); }

    @Test @DisplayName("BS P4 – target in right half")
    void bs_RightHalf() { assertEquals(4, new BinarySearch().search(new int[]{1,3,5,7,9}, 9)); }

    @Test @DisplayName("BS P5 – target not in array → -1")
    void bs_NotFound() { assertEquals(-1, new BinarySearch().search(new int[]{2,4,6,8}, 5)); }

    @Test @DisplayName("BS – single element found")
    void bs_SingleFound() { assertEquals(0, new BinarySearch().search(new int[]{42}, 42)); }

    @Test @DisplayName("BS – single element not found")
    void bs_SingleMiss() { assertEquals(-1, new BinarySearch().search(new int[]{42}, 1)); }

    @Test @DisplayName("BS – first element of large array")
    void bs_LargeFirst() {
        int[] arr = new int[50];
        for (int i = 0; i < 50; i++) arr[i] = i * 2;
        assertEquals(0, new BinarySearch().search(arr, 0));
    }

    @Test @DisplayName("BS – last element of large array")
    void bs_LargeLast() {
        int[] arr = new int[50];
        for (int i = 0; i < 50; i++) arr[i] = i * 2;
        assertEquals(49, new BinarySearch().search(arr, 98));
    }

    // ═══════════════════════════════════════════════════════
    // LINEAR SEARCH (int)
    // CFG Paths:
    //   P1: null or empty         → -1
    //   P2: found at index 0
    //   P3: found at last index
    //   P4: not found             → -1
    //   P5: unsorted array — still works
    // ═══════════════════════════════════════════════════════

    @Test @DisplayName("LS P1a – null array → -1")
    void ls_Null() { assertEquals(-1, new LinearSearch().search(null, 5)); }

    @Test @DisplayName("LS P1b – empty array → -1")
    void ls_Empty() { assertEquals(-1, new LinearSearch().search(new int[]{}, 5)); }

    @Test @DisplayName("LS P2 – found at index 0")
    void ls_AtStart() { assertEquals(0, new LinearSearch().search(new int[]{10,20,30}, 10)); }

    @Test @DisplayName("LS P3 – found at last index")
    void ls_AtEnd() { assertEquals(2, new LinearSearch().search(new int[]{10,20,30}, 30)); }

    @Test @DisplayName("LS P4 – not found → -1")
    void ls_NotFound() { assertEquals(-1, new LinearSearch().search(new int[]{10,20,30}, 99)); }

    @Test @DisplayName("LS P5 – works on unsorted array")
    void ls_Unsorted() { assertEquals(2, new LinearSearch().search(new int[]{50,10,80,20}, 80)); }

    @Test @DisplayName("LS – found in the middle")
    void ls_Middle() { assertEquals(1, new LinearSearch().search(new int[]{5,15,25,35}, 15)); }

    // LINEAR SEARCH (String)
    @Test @DisplayName("LS String – null array → -1")
    void ls_Str_Null() { assertEquals(-1, new LinearSearch().searchString(null, "a")); }

    @Test @DisplayName("LS String – null target → -1")
    void ls_Str_NullTarget() { assertEquals(-1, new LinearSearch().searchString(new String[]{"a"}, null)); }

    @Test @DisplayName("LS String – found")
    void ls_Str_Found() {
        assertEquals(1, new LinearSearch().searchString(new String[]{"apple","banana","cherry"}, "banana"));
    }

    @Test @DisplayName("LS String – not found → -1")
    void ls_Str_NotFound() {
        assertEquals(-1, new LinearSearch().searchString(new String[]{"apple","banana"}, "mango"));
    }

    // ═══════════════════════════════════════════════════════
    // BFS
    // CFG Paths:
    //   P1: null/empty graph           → null
    //   P2: start node missing         → null
    //   P3: start == target            → start node
    //   P4: target is direct neighbour → target node
    //   P4b: target several hops away  → target node
    //   P5: target not in graph        → null
    //   P6: disconnected graph         → null
    // ═══════════════════════════════════════════════════════

    // Helper: 1-2-3-4-5 linear graph
    private Graph linear() {
        Graph g = new Graph();
        for (int i = 1; i <= 5; i++) g.addNode(new GraphNode(i, "N"+i));
        g.addEdge(1,2); g.addEdge(2,3); g.addEdge(3,4); g.addEdge(4,5);
        return g;
    }

    // Helper: branching graph 1-2-4 / 1-3-5-4
    private Graph branching() {
        Graph g = new Graph();
        for (int i = 1; i <= 5; i++) g.addNode(new GraphNode(i, "N"+i));
        g.addEdge(1,2); g.addEdge(1,3); g.addEdge(2,4); g.addEdge(3,5); g.addEdge(4,5);
        return g;
    }

    @Test @DisplayName("BFS P1a – null graph → null")
    void bfs_NullGraph() { assertNull(new BreadthFirstSearch().search(null, 1, 3)); }

    @Test @DisplayName("BFS P1b – empty graph → null")
    void bfs_EmptyGraph() { assertNull(new BreadthFirstSearch().search(new Graph(), 1, 3)); }

    @Test @DisplayName("BFS P2 – start node not in graph → null")
    void bfs_StartMissing() { assertNull(new BreadthFirstSearch().search(linear(), 99, 3)); }

    @Test @DisplayName("BFS P3 – start equals target → returns node")
    void bfs_SameNode() {
        GraphNode r = new BreadthFirstSearch().search(linear(), 3, 3);
        assertNotNull(r);
        assertEquals(3, r.getId());
    }

    @Test @DisplayName("BFS P4a – direct neighbour found")
    void bfs_DirectNeighbour() {
        GraphNode r = new BreadthFirstSearch().search(linear(), 1, 2);
        assertNotNull(r);
        assertEquals(2, r.getId());
    }

    @Test @DisplayName("BFS P4b – target 4 hops away")
    void bfs_MultiHop() {
        GraphNode r = new BreadthFirstSearch().search(linear(), 1, 5);
        assertNotNull(r);
        assertEquals(5, r.getId());
    }

    @Test @DisplayName("BFS P5 – target ID not in graph → null")
    void bfs_TargetMissing() { assertNull(new BreadthFirstSearch().search(linear(), 1, 99)); }

    @Test @DisplayName("BFS P6 – disconnected graph → null")
    void bfs_Disconnected() {
        Graph g = new Graph();
        g.addNode(new GraphNode(1,"A"));
        g.addNode(new GraphNode(2,"B")); // no edge
        assertNull(new BreadthFirstSearch().search(g, 1, 2));
    }

    @Test @DisplayName("BFS – branching graph finds correct node")
    void bfs_Branching() {
        GraphNode r = new BreadthFirstSearch().search(branching(), 1, 5);
        assertNotNull(r);
        assertEquals(5, r.getId());
    }

    // shortestPath
    @Test @DisplayName("BFS shortestPath – null graph → empty")
    void sp_Null() { assertTrue(new BreadthFirstSearch().shortestPath(null,1,3).isEmpty()); }

    @Test @DisplayName("BFS shortestPath – same node → [id]")
    void sp_Same() { assertEquals(List.of(2), new BreadthFirstSearch().shortestPath(linear(),2,2)); }

    @Test @DisplayName("BFS shortestPath – direct path → [1,2]")
    void sp_Direct() { assertEquals(List.of(1,2), new BreadthFirstSearch().shortestPath(linear(),1,2)); }

    @Test @DisplayName("BFS shortestPath – 5-node path")
    void sp_Long() { assertEquals(List.of(1,2,3,4,5), new BreadthFirstSearch().shortestPath(linear(),1,5)); }

    @Test @DisplayName("BFS shortestPath – no path → empty")
    void sp_NoPath() {
        Graph g = new Graph();
        g.addNode(new GraphNode(1,"A")); g.addNode(new GraphNode(2,"B"));
        assertTrue(new BreadthFirstSearch().shortestPath(g,1,2).isEmpty());
    }

    // reachableNodes
    @Test @DisplayName("BFS reachableNodes – isolated node → [itself]")
    void rn_Isolated() {
        Graph g = new Graph(); g.addNode(new GraphNode(7,"Solo"));
        assertEquals(List.of(7), new BreadthFirstSearch().reachableNodes(g,7));
    }

    @Test @DisplayName("BFS reachableNodes – all 5 nodes reachable")
    void rn_AllReachable() {
        List<Integer> r = new BreadthFirstSearch().reachableNodes(linear(), 1);
        assertEquals(5, r.size());
    }

    // ═══════════════════════════════════════════════════════
    // ORDERSEARCH — integration tests (all 3 algorithms)
    // ═══════════════════════════════════════════════════════

    @Test @DisplayName("OrderSearch – findOrderById (BinarySearch)")
    void os_Binary() {
        Order[] orders = {
                new Order(1,"Alice",100,"PENDING"),
                new Order(3,"Bob",200,"SHIPPED"),
                new Order(5,"Carol",300,"DELIVERED")
        };
        Order r = new OrderSearch().findOrderById(orders, 3);
        assertNotNull(r);
        assertEquals("Bob", r.getCustomerName());
    }

    @Test @DisplayName("OrderSearch – findOrderById not found → null")
    void os_Binary_Miss() {
        assertNull(new OrderSearch().findOrderById(new Order[]{new Order(1,"A",100,"PENDING")}, 99));
    }

    @Test @DisplayName("OrderSearch – findOrdersByCustomer (LinearSearch)")
    void os_Linear_Customer() {
        Order[] orders = {
                new Order(1,"Alice",100,"PENDING"),
                new Order(2,"Bob",200,"SHIPPED"),
                new Order(3,"Alice",150,"PROCESSING")
        };
        List<Order> r = new OrderSearch().findOrdersByCustomer(orders, "Alice");
        assertEquals(2, r.size());
    }

    @Test @DisplayName("OrderSearch – findOrdersByCustomer null → empty list")
    void os_Linear_Null() {
        assertTrue(new OrderSearch().findOrdersByCustomer(null, "Alice").isEmpty());
    }

    @Test @DisplayName("OrderSearch – findFirstByStatus (LinearSearch)")
    void os_Linear_Status() {
        Order[] orders = {
                new Order(1,"Alice",100,"DELIVERED"),
                new Order(2,"Bob",200,"PENDING")
        };
        Order r = new OrderSearch().findFirstByStatus(orders, "PENDING");
        assertNotNull(r);
        assertEquals("Bob", r.getCustomerName());
    }

    @Test @DisplayName("OrderSearch – findOrderNodeBFS (BFS)")
    void os_BFS() {
        GraphNode r = new OrderSearch().findOrderNodeBFS(linear(), 1, 4);
        assertNotNull(r);
        assertEquals(4, r.getId());
    }

    @Test @DisplayName("OrderSearch – findOrderPath (BFS shortest path)")
    void os_Path() {
        assertEquals(List.of(1,2,3,4), new OrderSearch().findOrderPath(linear(), 1, 4));
    }
}