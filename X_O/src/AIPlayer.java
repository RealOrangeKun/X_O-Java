import java.util.Random;

public class AIPlayer {

    private char symbol;

    private char[][] consoleBoard;


    AIPlayer() {
        consoleBoard = new char[][]{
                new char[]{' ', ' ', ' '},
                new char[]{' ', ' ', ' '},
                new char[]{' ', ' ', ' '},
        };
    }

    public int[] makeMove() {
        if (isValidMove(1, 1)) {
            updateBoard(1,1,'X');
            return new int[]{1, 1};
        }
        else {
            int[] bestMove = minimax(consoleBoard);
            if (!isValidMove(bestMove[0], bestMove[1])) {
                int random1 = 1, random2 = 1;
                while (!isValidMove(random1, random2)) {
                    random1 = new Random().nextInt(0, 2);
                    random2 = new Random().nextInt(0, 2);
                }
                bestMove = new int[]{
                        random1,
                        random2
                };
            }
            consoleBoard[bestMove[0]][bestMove[1]] = 'X';
            return bestMove;
        }
    }

    private int[] minimax(char[][] board) {
        char opponent = 'X';

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

    private int minimaxScore(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);

        // Check for terminal states (win/lose/tie) or reach a specified depth
        if (score != 0 || depth == 2) {
            return score;
        }

        char opponent = 'X';

        // Recursively explore possible moves
        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == '\0') {
                        board[i][j] = symbol;
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
                    if (board[i][j] == '\0') {
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

    private int evaluate(char[][] board) {
        if (isWinner(board, symbol)) {
            return 10; // AI wins
        } else if (isWinner(board, 'X')) {
            return -10; // Opponent wins
        } else {
            return 0; // It's a tie or the game is not over yet
        }
    }

    public char[][] getConsoleBoard() {
        return consoleBoard;
    }

    public void updateBoard(int x, int y, char c) {
        this.consoleBoard[x][y] = c;
    }

    public char getSymbol() {
        return symbol;
    }

    private boolean isValidMove(int x, int y) {
        if (x < 0 || y < 0 || x > 2 || y > 2 ) return false;
        return consoleBoard[x][y] == 32;
    }

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
