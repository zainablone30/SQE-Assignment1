package search;

import java.util.*;

/**
 * Graph represents an undirected graph using an adjacency list.
 */
public class Graph {

    private Map<Integer, GraphNode>    nodes;
    private Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        nodes         = new HashMap<>();
        adjacencyList = new HashMap<>();
    }

    public void addNode(GraphNode node) {
        if (node == null) return;
        nodes.put(node.getId(), node);
        adjacencyList.putIfAbsent(node.getId(), new ArrayList<>());
    }

    public void addEdge(int fromId, int toId) {
        if (!nodes.containsKey(fromId) || !nodes.containsKey(toId)) return;
        adjacencyList.get(fromId).add(toId);
        adjacencyList.get(toId).add(fromId);
    }

    public GraphNode       getNode(int id)       { return nodes.get(id); }
    public List<Integer>   getNeighbours(int id) { return adjacencyList.getOrDefault(id, new ArrayList<>()); }
    public boolean         containsNode(int id)  { return nodes.containsKey(id); }
    public boolean         isEmpty()             { return nodes.isEmpty(); }
    public Map<Integer, GraphNode> getNodes()    { return nodes; }
}