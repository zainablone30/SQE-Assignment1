package search;

/**
 * GraphNode represents a single node in a graph.
 */
public class GraphNode {

    private int    id;
    private String label;

    public GraphNode(int id, String label) {
        this.id    = id;
        this.label = label;
    }

    public int    getId()    { return id; }
    public String getLabel() { return label; }

    @Override
    public String toString() { return "Node(" + id + ", " + label + ")"; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GraphNode)) return false;
        return this.id == ((GraphNode) obj).id;
    }

    @Override
    public int hashCode() { return Integer.hashCode(id); }
}