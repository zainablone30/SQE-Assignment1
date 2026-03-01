package search;

import java.util.*;

/**
 * Breadth-First Search (BFS) on a Graph.
 * Explores nodes level by level using a Queue (FIFO).
 * Guarantees shortest path in an unweighted graph.
 *
 * Time Complexity : O(V + E)
 * Space Complexity: O(V)
 */
public class BreadthFirstSearch {

    /**
     * Search for targetId starting from startId.
     * @return GraphNode if found, null otherwise
     */
    public GraphNode search(Graph graph, int startId, int targetId) {
        if (graph == null || graph.isEmpty())       return null;
        if (!graph.containsNode(startId))           return null;
        if (startId == targetId)                    return graph.getNode(startId);

        Queue<Integer> queue   = new LinkedList<>();
        Set<Integer>   visited = new HashSet<>();

        queue.add(startId);
        visited.add(startId);

        while (!queue.isEmpty()) {
            int currentId = queue.poll();

            for (int neighbourId : graph.getNeighbours(currentId)) {
                if (!visited.contains(neighbourId)) {
                    if (neighbourId == targetId) {
                        return graph.getNode(neighbourId);
                    }
                    visited.add(neighbourId);
                    queue.add(neighbourId);
                }
            }
        }

        return null;
    }

    /**
     * Returns shortest path (list of node IDs) from startId to targetId.
     * Returns empty list if no path exists.
     */
    public List<Integer> shortestPath(Graph graph, int startId, int targetId) {
        if (graph == null || graph.isEmpty() || !graph.containsNode(startId))
            return new ArrayList<>();

        if (startId == targetId)
            return Collections.singletonList(startId);

        Queue<Integer>        queue    = new LinkedList<>();
        Map<Integer, Integer> parentOf = new HashMap<>();
        Set<Integer>          visited  = new HashSet<>();

        queue.add(startId);
        visited.add(startId);
        parentOf.put(startId, null);

        boolean found = false;

        while (!queue.isEmpty() && !found) {
            int currentId = queue.poll();
            for (int neighbourId : graph.getNeighbours(currentId)) {
                if (!visited.contains(neighbourId)) {
                    visited.add(neighbourId);
                    parentOf.put(neighbourId, currentId);
                    queue.add(neighbourId);
                    if (neighbourId == targetId) { found = true; break; }
                }
            }
        }

        if (!found) return new ArrayList<>();

        List<Integer> path    = new ArrayList<>();
        Integer       current = targetId;
        while (current != null) {
            path.add(0, current);
            current = parentOf.get(current);
        }
        return path;
    }

    /**
     * Returns all node IDs reachable from startId in BFS order.
     */
    public List<Integer> reachableNodes(Graph graph, int startId) {
        List<Integer> result = new ArrayList<>();
        if (graph == null || graph.isEmpty() || !graph.containsNode(startId))
            return result;

        Queue<Integer> queue   = new LinkedList<>();
        Set<Integer>   visited = new HashSet<>();

        queue.add(startId);
        visited.add(startId);

        while (!queue.isEmpty()) {
            int currentId = queue.poll();
            result.add(currentId);
            for (int neighbourId : graph.getNeighbours(currentId)) {
                if (!visited.contains(neighbourId)) {
                    visited.add(neighbourId);
                    queue.add(neighbourId);
                }
            }
        }
        return result;
    }
}