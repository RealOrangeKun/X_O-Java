import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>The XO Board class which represents the board and handles the game logic and the user inputs (moves).</p>
 *
 * @author Youssef Tarek
 * @version 2.0
 * @see JFrame
 * @see MouseListener
 * @see ActionListener
 * @see Image
 */
public class XOBoard extends JFrame implements ActionListener, MouseListener {
    /**
     * Private final instance {@link Color} representing the background color for the XO Board.
     *
     * @see Color#Color(int, int, int)
     */
    private Color backgroundColor = new Color(1, 2, 64);
    /**
     * Private final {@link Image} representing the icon of the XO Board
     *
     * @see Image
     * @see ImageIcon
     * @see ImageIcon#ImageIcon(String)
     */
    private final Image icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);
    /**
     * Private final 2D array carrying {@link JButton}s of size 3x3 which is the standard XO Board.
     *
     * @see JButton
     * @see Arrays
     */
    private final JButton[][] buttons = new JButton[][]{
            new JButton[]{
                    new JButton(),
                    new JButton(),
                    new JButton()
            },
            new JButton[]{
                    new JButton(),
                    new JButton(),
                    new JButton()
            },
            new JButton[]{
                    new JButton(),
                    new JButton(),
                    new JButton(),
            }
    };

    /**
     * Private final instance of the {@link JPanel}s in the XO Board.
     *
     * @see JPanel
     */
    private final JPanel middlepanel, topPanel;
    /**
     * Private final instance of the {@link JLabel} at the top with the text that informs the player of who's turn it is.
     *
     * @see JLabel
     */
    private final JLabel label;
    /**
     * Private integer number representing the number of moves made on the board.
     */
    private int NOfMoves;

    /**
     * <p>Creates a new XO Board that isn't visible by default.</p>
     * <p>Use {@link JFrame#setVisible(boolean)} to make the board visible.</p>
     * @see GridLayout
     * @see FlowLayout
     * @see EmptyBorder
     * @see Dimension
     * @see Toolkit#getDefaultToolkit() 
     * @see WindowEvent
     * @see Arrays#stream(Object[]) 
     */
    XOBoard() {
        // Initialize the number of moves.
        NOfMoves = 0;
        // Initialize the middle panel.
        middlepanel = new JPanel(new GridLayout(3, 3, 20, 20));
        // Loop through every Jbutton array.
        Arrays.stream(buttons).forEach(ab -> {
            // Loop through every button and add the mouse listeners and set the default values then add it to the panel.
            Arrays.stream(ab).forEach(b -> {
                b.addMouseListener(this);
                b.setFocusable(false);
                b.setFont(new Font("Dubai", Font.BOLD, 40));
                b.setBackground(Color.WHITE);
                b.addActionListener(this);
                middlepanel.add(b);
            });
        });
        // Set size of the buttons.
        buttons[0][0].setPreferredSize(new Dimension(150, 150));
        // Set the border of the middle panel.
        middlepanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        // Set the background color.
        middlepanel.setBackground(backgroundColor);
        // Initialize the top panel with a FlowLayout.
        topPanel = new JPanel(new FlowLayout());
        // Set the border.
        topPanel.setBorder(new EmptyBorder(20, 20, 0, 20));
        // Create a label that will inform the player of the turn.
        label = new JLabel(Controller.getPlayer() == null ? "X's Turn" : "O's Turn");
        // Add the label to the top.
        topPanel.add(label);
        // Set the background color
        topPanel.setBackground(backgroundColor);
        // Set the font,
        label.setFont(new Font("Dubai", Font.BOLD, 45));
        // Get the screen size.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Override the exit button to call the ExitPrompt function.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Controller.ExitPrompt();
            }
        });
        // Set the size of the window.
        this.setSize(new Dimension(750, 750));
        // Set the title.
        this.setTitle("X O Game");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Set the location to the center of the screen.
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        // Set the layout to a BorderLayout
        this.setLayout(new BorderLayout());
        // Set the icon.
        this.setIconImage(icon);
        // Add the panels.
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlepanel, BorderLayout.CENTER);
        // Make it not resizable.
        this.setResizable(false);
        this.setBackground(backgroundColor);
    }

    /**
     * <p>The function which resets everything in the board by setting the text on each button to an empty string
     * by using the {@link JButton#setText(String)} method and enables the buttons using {@link JButton#setEnabled(boolean)}.</p>
     * <p>Then sets the {@link XOBoard#NOfMoves} to 0.</p>
     * <p>The checks if the AI Player is null by using the {@link Controller#getPlayer()} method and if it's not null
     * it creates a new instance by using {@link Controller#setPlayer(AIPlayer)} and then calls the
     * {@link Controller#makeAIMove()} method to make the move.</p>
     * @param playAgain The boolean to see if the board is getting cleared because the user wants to play again or not.
     * @see Controller
     * @see JButton
     */
    public void ClearBoard(boolean playAgain) {
        Arrays.stream(buttons).forEach(ab -> {
            Arrays.stream(ab).forEach(b -> {
                b.setText("");
                b.setEnabled(true);
            });
        });
        label.setText(Controller.getPlayer() == null ? "X's Turn" : "O's Turn");
        NOfMoves = 0;
        if (Controller.getPlayer() != null && playAgain) {
            Controller.setPlayer(new AIPlayer());
            Controller.makeAIMove();
        }
    }

    /**
     * <p>Loop through every row,column and diagonal of buttons and checks the text in it by using
     * {@link JButton#getText()} and if it find 3 consecutive 'X' or 'O' then it calls the {@link
     * XOBoard#showGameEndPrompt(String)} to announce the winner if there is one.</p>
     * @return true if it finds a winner and false otherwise.
     * @see JButton#getText()
     */
    public boolean IsWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][1].getText().equals(buttons[i][2].getText())) {
                if (buttons[i][0].getText().equals("X")) {
                    showGameEndPrompt("X Wins!");
                    return true;
                } else if (buttons[i][0].getText().equals("O")) {
                    showGameEndPrompt("O Wins!");
                    return true;
                }
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[1][i].getText().equals(buttons[2][i].getText())) {
                if (buttons[0][i].getText().equals("X")) {
                    showGameEndPrompt("X Wins!");
                    return true;
                } else if (buttons[0][i].getText().equals("O")) {
                    showGameEndPrompt("O Wins!");
                    return true;
                }
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText())) {
            if (buttons[0][0].getText().equals("X")) {
                showGameEndPrompt("X Wins!");
                return true;
            } else if (buttons[0][0].getText().equals("O")) {
                showGameEndPrompt("O Wins!");
                return true;
            }
        }

        if (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText())) {
            if (buttons[0][2].getText().equals("X")) {
                showGameEndPrompt("X Wins!");
                return true;
            } else if (buttons[0][2].getText().equals("O")) {
                showGameEndPrompt("O Wins!");
                return true;
            }
        }

        return false;
    }

    /**
     * <p>Checks if the game is over or not.</p>
     * @return {@code NOofMoves >= 9 or} {@link XOBoard#IsWinner()}
     */
    public boolean GameIsOver() {
        return (NOfMoves >= 9 || IsWinner());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * <p>First checks if the game is over or not if its not then it loops to find the source of the event by using
     * {@link MouseEvent#getSource()} method and then sets the text of the button to X or O according to the turn and
     * disables the button by using {@link JButton#setEnabled(boolean)} and adds one to the {@link XOBoard#NOfMoves}.</p>
     * <p>Then checks if the {@link XOBoard#NOfMoves} is bigger than or equal to 9 and there is no winner then this means
     * a draw so it uses {@link XOBoard#showGameEndPrompt(String)} to announce the draw to the player.</p>
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Check if the game is not over
        if (!GameIsOver()) {
            // Loop through all the buttons on the game board
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    JButton b = buttons[i][j];
                    // Check if the mouse release event is on a button and the game is not over and the button is enabled
                    if (e.getSource() == b &&!GameIsOver() && b.isEnabled()) {
                        // Set the text of the button to "X" or "O" depending on the current player
                        b.setText((NOfMoves % 2 == 0)? "X" : "O");
                        // Disable the button
                        b.setEnabled(false);
                        // Increment the number of moves
                        NOfMoves++;
                        // Update the board with the current player's move
                        if (Controller.getPlayer()!= null) {
                            Controller.getPlayer().updateBoard(i, j, 'O');
                        }
                    }
                }
            }
        }

        // Check if the game is over due to a draw
        if (NOfMoves >= 9 &&!IsWinner()) {
            // Disable all the buttons on the game board
            DisableAllButtons();
            // Clear the label on the game board
            label.setText("  ");
            // Display the game end prompt with the message "Draw!"
            showGameEndPrompt("Draw!");
        }

        // Check if the game is over due to a win or a stalemate
        if (GameIsOver()) {
            // Disable all the buttons on the game board
            DisableAllButtons();
            // Clear the board
            ClearBoard(false);
        } else {
            // Set the text of the label to "X's Turn" or "O's Turn" depending on the current player
            label.setText(Controller.getPlayer() == null? ((NOfMoves % 2 == 0)? "X's Turn" : "O's Turn ") : "O's Turn");
        }

        // Check if it is the AI's turn and the game is not over
        if (NOfMoves % 2 == 0 && Controller.getPlayer()!= null) {
            // Make the AI's move
            Controller.makeAIMove();
            // Check if the game is over due to a win or a stalemate
            if (!IsWinner() && NOfMoves >= 9) {
                // Disable all the buttons on the game board
                DisableAllButtons();
                // Clear the label on the game board
                label.setText("  ");
                // Display the game end prompt with the message "Draw!"
                showGameEndPrompt("Draw!");
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Getter for the {@link XOBoard#buttons}.
     * @return Array of buttons {@link XOBoard#buttons}
     */
    public JButton[][] getButtons() {
        return buttons;
    }

    /**
     * Getter for the {@link XOBoard#NOfMoves}.
     * @return {@link XOBoard#NOfMoves}
     */
    public int getNOfMoves() {
        return NOfMoves;
    }

    /**
     * Displays a game end prompt to the user with options to play again, exit the game, or return to the main menu.
     *
     * @param message The message to display in the game end prompt.
     */
    private void showGameEndPrompt(String message) {
        // Disable all buttons on the game board
        DisableAllButtons();

        // Clear the label on the game board
        label.setText("  ");

        // Load the icon image
        Image icon2 = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(50, 50, 100);

        // Create an array of options for the user to choose from
        String[] options = {"Play Again", "Exit", "Main Menu"};

        // Create a label to display the message in the game end prompt
        JLabel prompt = new JLabel(message);
        prompt.setFont(new Font("Dubai", Font.BOLD, 16));

        // Display the game end prompt to the user with options to play again, exit the game, or return to the main menu
        int response = JOptionPane.showOptionDialog(null, prompt, "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(icon2), options, 0);

        // If the user chooses to play again, clear the game board and start a new game
        if (response == 0) {
            ClearBoard(true);
        }
        // If the user chooses to exit the game, exit the program
        else if (response == 1) {
            System.exit(0);
        }
        // If the user chooses to return to the main menu, hide the game end prompt and show the main menu
        else {
            this.setVisible(false);
            Controller.getMainMenu().setVisible(true);
        }
    }

    /**
     * Disables all buttons by using {@link JButton#setEnabled(boolean)}.
     */
    public void DisableAllButtons() {
        Arrays.stream(buttons).forEach(ab -> {
            Arrays.stream(ab).forEach(b -> {
                b.setEnabled(false);
            });
        });
    }

    /**
     * Changes the background color of the label and the panels by using {@link JPanel#setBackground(Color)}.
     *
     * @param newcolor The new background color to set for the game board and the label on the game board.
     */
    public void ChangeColor(Color newcolor) {
        // Set the background color of the game board and the label on the game board to the new color
        setBackground(newcolor);
        label.setBackground(newcolor);
        topPanel.setBackground(newcolor);
        middlepanel.setBackground(newcolor);
    }

    /**
     * Enables all the buttons by using {@link JButton#setEnabled(boolean)}.
     */
    public void EnableAllButtons() {
        Arrays.stream(buttons).forEach(ab -> {
            Arrays.stream(ab).forEach(b -> {
                if (Objects.equals(b.getText(), "")) {
                    b.setEnabled(true);
                }
            });
        });
    }

    /**
     * Setter for the {@link XOBoard#NOfMoves}.
     * @param NOfMoves The new number of moves.
     */
    public void setNOfMoves(int NOfMoves) {
        this.NOfMoves = NOfMoves;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Getter for the {@link XOBoard#label}
     * @return {@link XOBoard#label}.
     */
    public JLabel getLabel() {
        return label;
    }

}
