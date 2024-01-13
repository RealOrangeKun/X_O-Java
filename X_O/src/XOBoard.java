import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Objects;

public class XOBoard extends JFrame implements ActionListener, MouseListener {
    private Color backgroundColor = new Color(1, 2, 64);
    private final Image icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);

    private JButton[][] buttons = new JButton[][]{
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


    private JPanel middlepanel, topPanel;
    private JLabel label;
    private int NOfMoves;

    XOBoard() {
        NOfMoves = 0;
        middlepanel = new JPanel(new GridLayout(3, 3, 20, 20));
        Arrays.stream(buttons).forEach(ab -> {
            Arrays.stream(ab).forEach(b -> {
                b.addMouseListener(this);
                b.setFocusable(false);
                b.setFont(new Font("Dubai", Font.BOLD, 40));
                b.setBackground(Color.WHITE);
                b.addActionListener(this);
                middlepanel.add(b);
            });
        });
        buttons[0][0].setPreferredSize(new Dimension(150, 150));
        middlepanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        middlepanel.setBackground(backgroundColor);
        topPanel = new JPanel(new FlowLayout());
        topPanel.setBorder(new EmptyBorder(20, 20, 0, 20));
        label = new JLabel(Controller.getPlayer() == null ? "X's Turn": "O's Turn");
        topPanel.add(label);
        topPanel.setBackground(backgroundColor);
        label.setFont(new Font("Dubai", Font.BOLD, 45));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Controller.ExitPrompt();
            }
        });
        this.setSize(new Dimension(750, 750));
        this.setTitle("X O Game");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setLayout(new BorderLayout());
        this.setIconImage(icon);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlepanel, BorderLayout.CENTER);
        this.setResizable(false);
        this.setBackground(backgroundColor);
    }


    public void ClearBoard(boolean playAgain) {
        Arrays.stream(buttons).forEach(ab -> {
            Arrays.stream(ab).forEach(b -> {
                b.setText("");
                b.setEnabled(true);
            });
        });
        label.setText(Controller.getPlayer() == null ? "X's Turn": "O's Turn");
        NOfMoves = 0;
        if (Controller.getPlayer() != null && playAgain) {
            Controller.setPlayer(new AIPlayer());
            Controller.makeAIMove();
        }
    }

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


    public boolean GameIsOver() {
        return (NOfMoves >= 9 || IsWinner());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!GameIsOver()) {
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    JButton b = buttons[i][j];
                    if (e.getSource() == b && !GameIsOver() && b.isEnabled()) {
                        b.setText((NOfMoves % 2 == 0) ? "X" : "O");
                        b.setEnabled(false);
                        NOfMoves++;
                        if (Controller.getPlayer() != null) {
                            Controller.getPlayer().updateBoard(i, j, 'O');
                        }
                    }
                }
            }

        }

        if (NOfMoves >= 9 && !IsWinner()) {
            DisableAllButtons();
            label.setText("  ");
            showGameEndPrompt("Draw!");

        }
        if (GameIsOver()) {
            DisableAllButtons();
            ClearBoard(false);
        } else {
            label.setText(Controller.getPlayer() == null ? ((NOfMoves %2 == 0) ? "X's Turn" : "O's Turn "): "O's Turn");
        }
        if (NOfMoves % 2 == 0 && Controller.getPlayer() != null) {
            Controller.makeAIMove();
            if (!IsWinner() && NOfMoves >= 9) {
                DisableAllButtons();
                label.setText("  ");
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

    public JButton[][] getButtons() {
        return buttons;
    }

    public int getNOfMoves() {
        return NOfMoves;
    }

    private void showGameEndPrompt(String message) {
        DisableAllButtons();
        label.setText("  ");
        Image icon2 = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(50, 50, 100);
        String[] s = {"Play Again", "Exit", "Main Menu"};
        JLabel prompt = new JLabel(message);
        prompt.setFont(new Font("Dubai", Font.BOLD, 16));
        int response = JOptionPane.showOptionDialog(null, prompt, "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(icon2), s, 0);
        if (response == 0) {
            ClearBoard(true);
        } else if (response == 1) {
            System.exit(0);
        } else {
            this.setVisible(false);
            Controller.getMainMenu().setVisible(true);
        }
    }

    public void DisableAllButtons() {
        Arrays.stream(buttons).forEach(ab -> {
            Arrays.stream(ab).forEach(b -> {
                b.setEnabled(false);
            });
        });
    }

    public void ChangeColor(Color newcolor) {
        setBackground(newcolor);
        label.setBackground(newcolor);
        topPanel.setBackground(newcolor);
        middlepanel.setBackground(newcolor);
    }

    public void updateBoard(int x, int y, char symbol) {
        buttons[x][y].setText(String.valueOf(symbol).toUpperCase());
    }

    public void EnableAllButtons() {
        Arrays.stream(buttons).forEach(ab -> {
            Arrays.stream(ab).forEach(b -> {
                if (Objects.equals(b.getText(), "")) {
                    b.setEnabled(true);
                }
            });
        });
    }

    public void setNOfMoves(int NOfMoves) {
        this.NOfMoves = NOfMoves;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public JLabel getLabel() {
        return label;
    }

}
