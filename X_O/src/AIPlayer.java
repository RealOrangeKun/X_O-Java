import java.util.Random;

/**
 * <p>The class for the AI Player that plays against the user using MiniMax algorithm.</p>
 *
 * @author Youssef Tarek
 * @version 2.0
 */
public class AIPlayer {

    /**
     * The symbol used to represent the X player on the game board.
     */
    private final char symbol = 'X';

    /**
     * The 2D array that represents the game board. The first dimension represents the rows of the board, and the second dimension represents the columns of the board. The value at each position in the array represents the symbol of the player who has placed their symbol at that position on the board. If the value is ', then that position on the board is empty.
     */
    private char[][] consoleBoard;


    /**
     * Initializes a new instance of the AIPlayer class with an empty 3x3 Tic-Tac-Toe board.
     */
    public AIPlayer() {
        consoleBoard = new char[][]{
                new char[]{' ', ' ', ' '},
                new char[]{' ', ' ', ' '},
                new char[]{' ', ' ', ' '},
        };
    }

    /**
     * Generates the AI's move using the minimax algorithm.
     * If a winning move is available, it takes that move. Otherwise, it plays optimally using minimax.
     *
     * @return An array containing the AI's move coordinates [x, y].
     */
    public int[] makeMove() {
        // Attempt to win the game
        if (isValidMove(1, 1)) {
            updateBoard(1, 1, 'X');
            return new int[]{1, 1};
        } else {
            // Block opponent's winning moves
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (isValidMove(i, j)) {
                        updateBoard(i, j, 'X');
                        if (isWinner(consoleBoard, 'X')) {
                            return new int[]{i, j};
                        }
                        updateBoard(i, j, ' ');
                    }
                }
            }

            // Play optimally using minimax algorithm
            int[] bestMove = minimax(consoleBoard);
            while (!isValidMove(bestMove[0], bestMove[1]))
                bestMove = minimax(consoleBoard);
            consoleBoard[bestMove[0]][bestMove[1]] = 'X';
            return bestMove;
        }
    }

    /**
     * Implements the minimax algorithm to find the best move for the AI.
     *
     * @param board The current state of the game board.
     * @return An array containing the coordinates [x, y] of the best move.
     */
    private int[] minimax(char[][] board) {
        char opponent = 'O';

        int[] bestMove = {-1, -1};
        int bestScore = Integer.MIN_VALUE;

        // Loop through the board to evaluate each possible move
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') { // Check if the spot is empty
                    board[i][j] = 'X'; // Make the move

                    // Recursively evaluate the score for this move
                    int score = minimaxScore(board, 0, false);

                    // Undo the move
                    board[i][j] = ' ';

                    // Update the best move if necessary
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    /**
     * Evaluates the score of a move in the minimax algorithm.
     *
     * @param board        The current state of the game board.
     * @param depth        The depth of the recursive evaluation.
     * @param isMaximizing Indicates whether the AI is maximizing or minimizing.
     * @return The score of the move.
     */
    private int minimaxScore(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);

        // Check for terminal states (win/lose/tie) or reach a specified depth
        if (score != 0 || depth == 2) {
            return score;
        }

        char opponent = 'O';

        // Recursively explore possible moves
        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int currentScore = minimaxScore(board, depth + 1, false);
                        maxScore = Math.max(maxScore, currentScore);
                        board[i][j] = ' '; // Undo the move
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = opponent;
                        int currentScore = minimaxScore(board, depth + 1, true);
                        minScore = Math.min(minScore, currentScore);
                        board[i][j] = ' '; // Undo the move
                    }
                }
            }
            return minScore;
        }
    }

    /**
     * Evaluates the current state of the board.
     *
     * @param board The current state of the game board.
     * @return The score based on the game outcome.
     */
    private int evaluate(char[][] board) {
        if (isWinner(board, 'X')) {
            return 10; // AI wins
        } else if (isWinner(board, 'O')) {
            return -10; // Opponent wins
        } else {
            return 0; // It's a tie or the game is not over yet
        }
    }

    /**
     * Updates the game board with the specified move.
     *
     * @param x The x-coordinate of the move.
     * @param y The y-coordinate of the move.
     * @param c The symbol ('X' or 'O') representing the player making the move.
     */
    public void updateBoard(int x, int y, char c) {
        this.consoleBoard[x][y] = c;
    }

    /**
     * Checks if a move is valid at the specified coordinates.
     *
     * @param x The x-coordinate of the move.
     * @param y The y-coordinate of the move.
     * @return True if the move is valid, false otherwise.
     */
    private boolean isValidMove(int x, int y) {
        if (x < 0 || y < 0 || x > 2 || y > 2) return false;
        return consoleBoard[x][y] == ' ';
    }

    /**
     * Checks if a player has won the game.
     *
     * @param board  The current state of the game board.
     * @param player The player ('X' or 'O') to check for a win.
     * @return True if the player has won, false otherwise.
     */
    private boolean isWinner(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true; // Winner in the row
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true; // Winner in the column
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true; // Winner in the main diagonal
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true; // Winner in the anti-diagonal
        }

        return false; // No winner yet
    }
}
