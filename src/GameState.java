import java.util.ArrayList;
import java.util.Objects;

public class GameState {
    /**
     * Main Game State configurations,
     * 1. 2D array representing a 3x3 tile board
     * 2. Fields empty row and empty column to get the position of the empty cell, which is represented as 0
     * 3. Initial Board configuration to the one that was provided in the lab
     * 4. Goal Board, representing the goal of the 8 puzzle problem
     */

    private final int[][] board;
    private int emptyRow;
    private int emptyCol;
    static final int[][] INIT_BOARD = {
            {8, 7, 6},
            {5, 4, 3},
            {2, 1, 0}
    };

    static final int[][] GOAL_BOARD = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    private static final int[][] DIRECTIONS = {
            {-1, 0}, // up (row = -1, col = 0)
            {1, 0}, // down (row = +1, col = 0)
            {0, -1}, // left (row = 0, col = -1)
            {0, 1} // right (row = 0, col = +1)
    };

    public GameState(int[][] board) { // construct a new board set up
        this.board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = board[i][j];
                if (board[i][j] == 0) {
                    this.emptyRow = i;
                    this.emptyCol = j;
                }
            }
        }
    }

    /**
     * Clones the board inorder to generate possible successors from a given game state
     *
     * @return new GameState
     */
    public GameState cloneBoard() {
        int[][] clonedBoard = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clonedBoard[i][j] = this.board[i][j];
            }
        }
        return new GameState(clonedBoard);
    }

    public int getEmptyRow() {
        return this.emptyRow;
    }

    public int getEmptyCol() {
        return this.emptyCol;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void setEmptyRow(int newRow) {
        this.emptyRow = newRow;
    }

    public void setEmptyCol(int newCol) {
        this.emptyCol = newCol;
    }

    /**
     * Compares the current board configuration to the goal state
     *
     * @return False if not goal state, otherwise return True
     */
    public boolean isGoal() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] != GOAL_BOARD[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Generates all valid successors for a given game state by checking,
     * if a given empty position (0) can be moved in either of the 4 directions (up,down,left,right),
     * if empty position can be moved, it generates a successor and stores it in a list of possible moves
     *
     * @return ArrayList of GameState
     */
    public ArrayList<GameState> possibleMoves() {
        ArrayList<GameState> moves = new ArrayList<GameState>();

        // for each direction
        for (int[] direction : DIRECTIONS) {
            int newEmptyRow = emptyRow + direction[0];
            int newEmptyCol = emptyCol + direction[1];

            if (checkIfValidPosition(newEmptyRow, newEmptyCol)) {
                GameState newValidState = this.cloneBoard();

                // get the old empty position before placing move
                int oldRow = newValidState.getEmptyRow();
                int oldCol = newValidState.getEmptyCol();

                // swap values the titles with corresponding move, if up for example, than swap with the tile above
                newValidState.board[oldRow][oldCol] =
                        newValidState.board[newEmptyRow][newEmptyCol];

                // update the empty position to not have old value but to be 0
                newValidState.board[newEmptyRow][newEmptyCol] = 0;
                newValidState.setEmptyRow(newEmptyRow);
                newValidState.setEmptyCol(newEmptyCol);

                moves.add(newValidState);
            }
        }
        return moves;
    }

    /**
     * Helper method to check if a position if valid for the possibleMoves() method,
     * Check if the empty cell is within bounds of the board
     *
     * @return True if position is valid, otherwise return False
     */
    public boolean checkIfValidPosition(int r, int c) {
        if (r >= 0 && r < 3 && c >= 0 && c < 3) {
            return true;
        }
        return false;
    }

    /**
     * Defining a custom equals method to override the standard equals from Java.
     * Reason: Default objects are compared based on reference in memory, this is not suitable for a campirson in game states.
     * If two games states have identical board configurations but are two distinct objects, Java will rule this comparison as False.
     * This custom equals compares based on the game state board configurations and NOT reference in memory.
     *
     * @return True if two game states are equal, otherwise return false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameState)) return false;

        GameState other = (GameState) o;

        // Compare if two boards are the same
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] != other.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Generates a unique hash code for a given game state.
     * Reason: Faster and Correct lookups using a hashset
     * Two objects that have the same logical state of board configurations produce the same hash state,
     * rather than being based on memory address and object reference
     */
    @Override
    public int hashCode() {
        int hash = 6;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                hash = 31 * hash + this.board[i][j];
            }
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    sb.append("  ");
                } else {
                    sb.append(board[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
