public class Node implements Comparable<Node> {
    GameState state;
    Node parent;
    private int cost;
    private int heuristic;
    private boolean isAStar;

    /**
     * Node constructor used to generate successors with updated cost and heuristic
     * */
    public Node(GameState state, Node parent, int cost, int heuristic, boolean isAStar) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
        this.isAStar = isAStar;
    }

    /**
     * Node constructor used to create a Node with initial configuration (root)
     * */
    public Node(GameState state, Node parent, int cost, int heuristic) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic = heuristic;
    }

    /**
     * Node constructor used to generate successors with updated cost
     * */
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
    /**
     * Override compareTo method defines natural ordering for Priority Queue.
     * For A*, the f(n) is compared,
     * For UCS, the g(n) is compared.
     * */
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

    /**
     * Defining a custom equals method to override the standard equals from Java.
     * Reason: Default objects are compared based on reference in memory, this is not suitable for a campirson in game states.
     * If two games states have identical board configurations but are two distinct objects, Java will rule this comparison as False.
     * This custom equals compares based on the game state board configurations and NOT reference in memory.
     *
     * @return True if two Nodes have identical game states
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Node)) return false;

        Node other = (Node) o;
        return this.state.equals(other.state);
    }

    /**
     * Generates a unique hash code for a given Node based on it's game state
     * Reason: Faster and Correct lookups using a hashset
     * Two objects that have the same logical state of board configurations produce the same hash state,
     * rather than being based on memory address and object reference.
     *
     */
    @Override
    public int hashCode(){
        return this.state.hashCode();
    }

    @Override
    public String toString() {
        return "Node with cost=" + cost + ":\n" + state;
    }
}
