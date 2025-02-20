import java.io.PrintWriter;
import java.util.*;

public class AStar extends Solver {

    public AStar(int[][] initialBoard) {
        super(initialBoard);

    }

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

            ArrayList<GameState> successors = current.state.possibleMoves();

            for (GameState successor : successors) {

                Node child = new Node(successor, current, current.getCost() + 1, calculateHeuristic(successor), true);

                if (!openSet.contains(child) && !expanded.contains(child)) {
                    unexpanded.add(child);
                    openSet.add(child);
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
                    int distanceCol = Math.abs(j - getGoalColForPosition);

                    distance += (distanceRow + distanceCol);

                }
            }
        }
        return distance;
    }

}
