import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {
    PriorityQueue<Node> unexpanded;
    HashSet<Node> expanded;
    Node root;

    public Solver(int[][] initialBoard) {
        GameState initialState = new GameState(initialBoard);
        root = new Node(initialState, false); // UCS
        unexpanded = new PriorityQueue<>(Node::compareTo);
        expanded = new HashSet<>();
    }

    public void solveWithUCS(PrintWriter output) {
        int numberOfExpansion = 0;
        unexpanded.add(root);


        while(!unexpanded.isEmpty()){
            Node poll = unexpanded.poll();
            expanded.add(poll);
            numberOfExpansion ++;

            // Print a debug message every 5000 expansions
            if (numberOfExpansion % 5000 == 0) {
                System.out.println("Expanded " + numberOfExpansion + " nodes so far...");
                System.out.println("Current node cost: " + poll.getCost());
                System.out.println(poll.state);
            }

            if(poll.state.isGoal()){
                reportSolution(poll,output);
                return;
            }

            ArrayList<GameState> successors = poll.state.possibleMoves();

            for(GameState successor : successors){
                if(Node.findNodeWithState(expanded,successor) == null
                        && Node.findNodeWithState(unexpanded, successor) == null){

                    Node child = new Node(successor,poll);
                    unexpanded.add(child);
                }
            }
        }
        output.println("No solution found");
    }

    public void printSolution(Node n, PrintWriter output) {
        if (n.parent != null) printSolution(n.parent, output);
        output.println(n.state);
    }

    public void reportSolution(Node n, PrintWriter output) {
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.expanded.size());
        output.println("Nodes unexpanded: " + this.unexpanded.size());
        output.println();
    }

    public static void main(String[] args) throws Exception {
        Solver problem = new Solver(GameState.INIT_BOARD);
        File outFile = new File("output.txt");
        PrintWriter output = new PrintWriter(outFile);
        problem.solveWithUCS(output);
        output.close();
    }
}
