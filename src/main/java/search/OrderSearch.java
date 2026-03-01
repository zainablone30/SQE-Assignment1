package search;

import java.util.*;

/**
 * OrderSearch integrates all three search algorithms:
 *   1. BinarySearch  — find order by ID in a sorted array
 *   2. LinearSearch  — find orders by customer name (unsorted)
 *   3. BreadthFirstSearch — find order node in a dependency graph
 */
public class OrderSearch {

    private final BinarySearch       binarySearch = new BinarySearch();
    private final LinearSearch       linearSearch = new LinearSearch();
    private final BreadthFirstSearch bfs          = new BreadthFirstSearch();

    // ── 1. Binary Search ────────────────────────────────────

    /**
     * Find an order by ID using Binary Search (array must be sorted by ID).
     */
    public Order findOrderById(Order[] orders, int targetId) {
        if (orders == null || orders.length == 0) return null;
        int[] ids = new int[orders.length];
        for (int i = 0; i < orders.length; i++) ids[i] = orders[i].getOrderId();
        int idx = binarySearch.search(ids, targetId);
        return (idx != -1) ? orders[idx] : null;
    }

    // ── 2. Linear Search ────────────────────────────────────

    /**
     * Find all orders for a given customer name using Linear Search.
     * Works on unsorted arrays.
     */
    public List<Order> findOrdersByCustomer(Order[] orders, String customerName) {
        List<Order> result = new ArrayList<>();
        if (orders == null || customerName == null) return result;
        for (Order order : orders) {
            if (customerName.equals(order.getCustomerName())) {
                result.add(order);
            }
        }
        return result;
    }

    /**
     * Find an order by status using Linear Search.
     * Returns the first order matching the given status.
     */
    public Order findFirstByStatus(Order[] orders, String status) {
        if (orders == null || status == null) return null;
        for (Order order : orders) {
            if (status.equals(order.getStatus())) return order;
        }
        return null;
    }

    // ── 3. BFS ──────────────────────────────────────────────

    /**
     * Find an order node in a dependency graph using BFS.
     */
    public GraphNode findOrderNodeBFS(Graph graph, int startId, int targetId) {
        return bfs.search(graph, startId, targetId);
    }

    /**
     * Find shortest dependency path between two orders using BFS.
     */
    public List<Integer> findOrderPath(Graph graph, int startId, int targetId) {
        return bfs.shortestPath(graph, startId, targetId);
    }
}