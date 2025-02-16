import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Node implements Comparable<Node> {
    GameState state;
    Node parent;
    private int cost;
    private int heuristic;
    private boolean isAStar;

    public Node(GameState state, Node parent, int cost, int heuristic, boolean isAStar) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
        this.isAStar = isAStar;
    }

    public Node(GameState state, boolean isAStar) {
        this(state, null, 0, 0, isAStar);
    }

    public Node(GameState state, Node parent) {
        this.state = state;
        this.parent = parent;
        this.cost = (parent == null) ? 0 : parent.getCost() + 1;
    }

    public int getCost() {
        return this.cost;
    }

    public int getHeuristic() {
        return this.heuristic;
    }

    public static Node findNodeWithState(Iterable<Node> nodes, GameState gs) {
        for (Node n : nodes) {
            if (gs.sameBoard(n.state)) return n;
        }
        return null;
    }

    @Override
    public int compareTo(Node o) {
        if (this.isAStar) {
            int currentF = this.cost + this.heuristic;
            int otherF = o.cost + o.heuristic;
            return Integer.compare(currentF, otherF);
        } else {
            return Integer.compare(this.cost, o.cost);
        }
    }

    @Override
    public String toString() {
        return "Node with cost=" + cost + ":\n" + state;
    }
}
