import java.io.PrintWriter;
import java.util.*;

public abstract class Solver {
    protected Queue<Node> unexpanded;
    protected Set<Node> openSet;
    protected Set<Node> expanded;
    protected Node root;

    public Solver(int[][] initialBoard) {
        GameState initialState = new GameState(initialBoard);
        root = new Node(initialState, null, 0, 0);
    }

    public abstract void solve(PrintWriter output);

    protected void printSolution(Node n, PrintWriter output) {
        if (n.parent != null) printSolution(n.parent, output);
        output.println(n.state);
    }

    protected void reportSolution(Node n, PrintWriter output) {
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.expanded.size());
        output.println("Nodes unexpanded: " + this.unexpanded.size());
        output.println();
    }
}