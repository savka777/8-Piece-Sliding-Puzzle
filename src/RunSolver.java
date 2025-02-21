import java.io.File;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class RunSolver {
    public static void main(String[] args) {

        File outFileUCS = new File("src/outputUCS.txt");
        File outFileAStar = new File("src/outputAStar.txt");
        Scanner myObj = new Scanner(System.in);

        System.out.println("PRESS 1: A Star " + "or " + "PRESS 2: UCS");
        String choice = myObj.nextLine();
        try {
            if (Objects.equals(choice, "1")) {

                AStar problem = new AStar(GameState.INIT_BOARD);
                try (PrintWriter outputAStar = new PrintWriter(outFileAStar)) {
                    long startTime = System.nanoTime();
                    problem.solve(outputAStar);
                    long endTime   = System.nanoTime();
                    long totalTime = endTime - startTime;
                    double seconds = (double) totalTime / 1_000_000_000;
                    System.out.println("A* solution written to outputAStar.txt");
                    System.out.println("Time taken to run " + seconds + "s");
                }
            } else if (Objects.equals(choice, "2")) {
                UCS problem = new UCS(GameState.INIT_BOARD);
                try (PrintWriter outputUCS = new PrintWriter(outFileUCS)) {
                    long startTime = System.nanoTime();
                    problem.solve(outputUCS);
                    long endTime   = System.nanoTime();
                    long totalTime = endTime - startTime;
                    double seconds = (double) totalTime / 1_000_000_000;
                    System.out.println("UCS solution written to outputUCS.txt");
                    System.out.println("Time taken to run " + seconds + "s");
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

