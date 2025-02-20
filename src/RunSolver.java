import java.io.File;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class RunSolver {

    public static void main(String[] args) {
        File outFileUCS = new File("src/outputUCS.txt");
        File outFileAStar = new File("src/outputAStar.txt");
        Scanner myObj = new Scanner(System.in);
        System.out.println("PRESS 1: A* " + "\n" + "PRESS 2: UCS");
        String choice = myObj.nextLine();
        try {
            if (Objects.equals(choice, "1")) {
                AStar problem = new AStar(GameState.INIT_BOARD);
                try (PrintWriter outputAStar = new PrintWriter(outFileAStar)) {
                    problem.solve(outputAStar);
                    System.out.println("A* solution written to outputAStar.txt");
                }
            } else if (Objects.equals(choice, "2")) {
                UCS problem = new UCS(GameState.INIT_BOARD);
                try (PrintWriter outputUCS = new PrintWriter(outFileUCS)) {
                    problem.solve(outputUCS);
                }
            } else {
                System.out.println("Invalid choice. Exiting.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}