import java.io.PrintWriter;
import java.util.*;

public class UCS extends Solver{

    /**
     * UCS class that extends the solver, implements an abstract class Solve to generate a solution with the UCS Algorithm
     */
    public UCS(int[][] initialBoard) {super(initialBoard);}

    /**
     * UCS Solver,
     * Queue of unexpanded children
     * Hashset of expanded children to ensure no infinite cycles and redundant checks
     * Hashset of open set (unexpanded children, parallel to priority queue of unexpanded children, used for faster lookup compared to Priority Queue to avoid a linear scan
     *
     * @param output file
     */
    public void solve(PrintWriter output) {
        unexpanded = new LinkedList<>();
        expanded = new HashSet<>();
        openSet = new HashSet<>();

        unexpanded.add(root);
        openSet.add(root);

        while (!unexpanded.isEmpty()) {
            Node current = unexpanded.remove();
            openSet.remove(current);
            expanded.add(current);

            if (current.state.isGoal()) {
                reportSolution(current, output);
                return;
            }

            // generate all valid successors of the current nodes game state
            ArrayList<GameState> successors = current.state.possibleMoves();

            for (GameState successor : successors) {

                // Generate child, update cost (cost is updated in the constructor)
                Node child = new Node(successor, current);

                // Check if nodes game state has already been visited
                if (!openSet.contains(child) && !expanded.contains(child)) {
                    unexpanded.add(child);
                    openSet.add(child);
                }
            }
        }
        output.println("No solution found");
    }
}
