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

    public Node(GameState state, Node parent, int cost, int heuristic) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
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

    // Define natural ordering for priority queue (heuristic and cost (A*) / cost (UCS))
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

    // each node is compared based on it's game state
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Node)) return false;

        Node other = (Node) o;
        return this.state.equals(other.state);
    }

    @Override
    public int hashCode(){
        return this.state.hashCode();
    }

    @Override
    public String toString() {
        return "Node with cost=" + cost + ":\n" + state;
    }
}
