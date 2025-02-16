import java.util.ArrayList;

public class GameState {

    final int[][] board; // 3x3 board representing the 8 tile puzzle (could also use a 1d array but I thought 3x3 is more intuitive)
    private int emptyRow; // the row position of the empty cell (0)
    private int emptyCol; // the col position of the empty cell (0)
    static final int[][] INIT_BOARD = { // init board config, choose the same layout as the labs
            {8, 7, 6},
            {5, 4, 3},
            {2, 1, 0}
    };

    static final int[][] GOAL_BOARD = { // goal board
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    private static final int[][] DIRECTIONS = { // map the directions, since immutable declared here
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

    public GameState cloneBoard() { // clone the board, then later generate successors from cloned board
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

    public void setEmptyRow(int newRow) {
        this.emptyRow = newRow;
    }

    public void setEmptyCol(int newCol) {
        this.emptyCol = newCol;
    }

    public boolean isGoal() { // speaks for itself, check is the current board config is the goal state
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] != GOAL_BOARD[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean sameBoard(GameState g) { // checking to see if the board is the same, necessary for avoid cycles and optimization
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] != g.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<GameState> possibleMoves() { // generate successors (all possible valid moves)
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

    public boolean checkIfValidPosition(int r, int c) { // helper, making sure positions are valid on the 3x3 grid
        if (r >= 0 && r < 3 && c >= 0 && c < 3) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    sb.append("  "); // represent the blank
                } else {
                    sb.append(board[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
