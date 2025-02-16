import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Solver {
    PriorityQueue<Node> unexpanded;
    HashSet<Node> expanded;
    Node root;
    boolean isAStar;

    public Solver(int[][] initialBoard, boolean isAStar) {
        GameState initialState = new GameState(initialBoard);
        root = new Node(initialState, isAStar);
        unexpanded = new PriorityQueue<>(Node::compareTo);
        expanded = new HashSet<>();
    }

    public void solveWithAStar(PrintWriter output) {
        int numberOfExpansion = 0;
        unexpanded.add(root);

        while (!unexpanded.isEmpty()) {
            Node poll = unexpanded.poll();
            expanded.add(poll);
            numberOfExpansion++;

            // Print a debug message every 5000 expansions
            if (numberOfExpansion % 5000 == 0) {
                System.out.println("Expanded " + numberOfExpansion + " nodes so far...");
                System.out.println("Current node cost: " + poll.getCost());
                System.out.println(poll.state);
            }

            if (poll.state.isGoal()) {
                reportSolution(poll, output);
                return;
            }

            ArrayList<GameState> successors = poll.state.possibleMoves();

            for (GameState successor : successors) {
                if (Node.findNodeWithState(unexpanded, successor) == null &&
                        Node.findNodeWithState(expanded, successor) == null) {

                    Node child = new Node(successor, poll, poll.getCost() + 1, calculateHeuristic(successor),true);
                    unexpanded.add(child);
                }
            }
        }
        output.println("No solution found");
    }

    // Calculate how far the current board setup is from the final goal,
    // Take the cumulative measure of all the positions to there goal position
    private int calculateHeuristic(GameState gameState) {
        int distance = 0;
        int board[][] = gameState.getBoard();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int currentPositionValue = board[i][j];
                if (currentPositionValue != 0) {
                    int getGoalRowForPosition = (currentPositionValue - 1) / 3;
                    int getGoalColForPosition = (currentPositionValue - 1) % 3;

                    int distanceRow = Math.abs(i - getGoalRowForPosition);
                    int distanceCol = Math.abs(i - getGoalColForPosition);

                    distance += (distanceRow + distanceCol);

                }
            }
        }
        return distance;
    }

    public void solveWithUCS(PrintWriter output) {
        int numberOfExpansion = 0;
        unexpanded.add(root);


        while (!unexpanded.isEmpty()) {
            Node poll = unexpanded.poll();
            expanded.add(poll);
            numberOfExpansion++;

            // Print a debug message every 5000 expansions
            if (numberOfExpansion % 5000 == 0) {
                System.out.println("Expanded " + numberOfExpansion + " nodes so far...");
                System.out.println("Current node cost: " + poll.getCost());
                System.out.println(poll.state);
            }

            if (poll.state.isGoal()) {
                reportSolution(poll, output);
                return;
            }

            ArrayList<GameState> successors = poll.state.possibleMoves();

            for (GameState successor : successors) {
                if (Node.findNodeWithState(expanded, successor) == null
                        && Node.findNodeWithState(unexpanded, successor) == null) {

                    Node child = new Node(successor, poll);
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
        File outFileUCS = new File("outputUCS.txt");
        File outFileAStar = new File("outputAStar.txt");
        Scanner myObj = new Scanner(System.in);
        System.out.println("PRESS 1: A* " + "\n" + "PRESS 2: UCS");

        String chose = myObj.nextLine();
        if (Objects.equals(chose, "1")) {
            Solver problem = new Solver(GameState.INIT_BOARD, true);
            PrintWriter outputAStar = new PrintWriter(outFileAStar);
            problem.solveWithAStar(outputAStar);
            outputAStar.close();

        } else if (Objects.equals(chose, "2")) {
            Solver problem = new Solver(GameState.INIT_BOARD, false);
            PrintWriter outputUCS = new PrintWriter(outFileUCS);
            problem.solveWithUCS(outputUCS);
            outputUCS.close();
        }
    }
}
