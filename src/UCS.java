import java.io.PrintWriter;
import java.util.*;

public class UCS extends Solver{

    public UCS(int[][] initialBoard) {super(initialBoard);}

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

            ArrayList<GameState> successors = current.state.possibleMoves();

            for (GameState successor : successors) {
                Node child = new Node(successor, current);

                if (!openSet.contains(child) && !expanded.contains(child)) {
                    unexpanded.add(child);
                    openSet.add(child);
                }
            }
        }
        output.println("No solution found");
    }
}
