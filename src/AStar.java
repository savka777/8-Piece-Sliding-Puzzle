import java.io.PrintWriter;
import java.util.*;

public class AStar extends Solver {

    /**
     * A Star class that extends the solver, implements an abstract class Solve to generate a solution with the A Star algorithm
     */
    public AStar(int[][] initialBoard) {
        super(initialBoard);
    }

    /**
     * A Star Solver,
     * Priority Queue of unexpanded children with a custom comparable to retrieve priority based on f(n) = g(n) + g(h)
     * Hashset of expanded children to ensure no infinite cycles and redundant checks
     * Hashset of open set (unexpanded children, parallel to priority queue of unexpanded children, used for faster lookup compared to Priority Queue to avoid a linear scan
     *
     * @param output file
     */
    public void solve(PrintWriter output) {
        unexpanded = new PriorityQueue<>(Node::compareTo);
        expanded = new HashSet<>();
        openSet = new HashSet<>();
        unexpanded.add(root);
        openSet.add(root);

        while (!unexpanded.isEmpty()) {
            Node current = unexpanded.poll();
            openSet.remove(current);
            expanded.add(current);

            if (current.state.isGoal()) {
                reportSolution(current, output);
                return;
            }

            // generate all valid successors of the current nodes game state
            ArrayList<GameState> successors = current.state.possibleMoves();

            for (GameState successor : successors) {

                // Generate child, update cost, calculate heuristic
                Node child = new Node(successor, current, current.getCost() + 1, calculateHeuristic(successor), true);

                // Check if nodes game state has already been visited
                if (!openSet.contains(child) && !expanded.contains(child)) {
                    unexpanded.add(child);
                    openSet.add(child);
                }
            }
        }
        output.println("No solution found");
    }

    /**
     * Heuristic Function,
     * For every position on the board, calculates how far each cell is from it's goal position
     * For a given game state, the heuristic is determined based on the cumulative measure of each cell.
     *
     * @param gameState
     * @return Int, how far the board is from the goal configuration
     */
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
                    int distanceCol = Math.abs(j - getGoalColForPosition);

                    distance += (distanceRow + distanceCol);
                }
            }
        }
        return distance;
    }
}
