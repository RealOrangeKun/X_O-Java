import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class XOBoard extends JFrame implements ActionListener, MouseListener {
    private Color backgroundColor = new Color(1, 2, 64);
    private final Image icon = new ImageIcon("F:\\X_O-Java\\X_O\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);

    private JButton[] buttons = new JButton[]{
            new JButton(),
            new JButton(),
            new JButton(),
            new JButton(),
            new JButton(),
            new JButton(),
            new JButton(),
            new JButton(),
            new JButton(),
    };
    private JPanel middlepanel, topPanel;
    private JLabel label;
    private int NOfMoves;
    private JFrame mainmenu;

    XOBoard(JFrame menu) {
        mainmenu = menu;
        NOfMoves = 0;
        middlepanel = new JPanel(new GridLayout(3, 3, 20, 20));
        for (JButton b : buttons) {
            b.addMouseListener(this);
            b.setFocusable(false);
            b.setFont(new Font("Dubai", Font.BOLD, 40));
            b.setBackground(Color.WHITE);
            b.addActionListener(this);
            middlepanel.add(b);
        }
        buttons[0].setPreferredSize(new Dimension(150, 150));
        middlepanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        middlepanel.setBackground(backgroundColor);
        topPanel = new JPanel(new FlowLayout());
        topPanel.setBorder(new EmptyBorder(20, 20, 0, 20));
        label = new JLabel("     ");
        topPanel.add(label);
        topPanel.setBackground(backgroundColor);
        label.setFont(new Font("Dubai", Font.BOLD, 45));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.setSize(new Dimension(750, 750));
        this.setTitle("X O Game");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Image icon2 = new ImageIcon(System.getProperty("user.dir")+"\\src\\resources\\icon.png").getImage().getScaledInstance(50, 50, 100);
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(icon2));
                if (response == 0) {
                    System.exit(0);
                }
            }
        });
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setLayout(new BorderLayout());
        this.setIconImage(icon);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlepanel, BorderLayout.CENTER);
        if (SettingsMenu.IsFullScreen()) {
            toggleFullScreen(this);
        }
        this.setResizable(false);
        this.setBackground(backgroundColor);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton b : buttons) {
            if (e.getSource() == b && !GameIsOver()) {
                b.setText((NOfMoves % 2 == 0) ? "X" : "O");
                b.setEnabled(false);
                NOfMoves++;
            }
        }
    }


    private void ClearBoard() {
        for (JButton b : buttons) {
            b.setText("");
            b.setEnabled(true);
        }
        label.setText("     ");
        NOfMoves = 0;
    }

    private boolean IsWinner() {
        for (int i = 0; i < 3; i++) {
            int startIndex = i * 3;
            if (buttons[startIndex].getText().equals(buttons[startIndex + 1].getText()) &&
                    buttons[startIndex + 1].getText().equals(buttons[startIndex + 2].getText())) {
                if (buttons[startIndex].getText().equals("X")) {
                    label.setText("X Wins!");
                    return true;
                } else if (buttons[startIndex].getText().equals("O")) {
                    label.setText("O Wins!");
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (buttons[i].getText().equals(buttons[i + 3].getText()) &&
                    buttons[i + 3].getText().equals(buttons[i + 6].getText())) {
                if (buttons[i].getText().equals("X")) {
                    label.setText("X Wins!");
                    return true;
                } else if (buttons[i].getText().equals("O")) {
                    label.setText("O Wins!");
                    return true;
                }
            }
        }
        if (buttons[0].getText().equals(buttons[4].getText()) && buttons[4].getText().equals(buttons[8].getText())) {
            if (buttons[0].getText().equals("X")) {
                label.setText("X Wins!");
                return true;
            } else if (buttons[0].getText().equals("O")) {
                label.setText("O Wins!");
                return true;
            }
        }
        if (buttons[2].getText().equals(buttons[4].getText()) && buttons[4].getText().equals(buttons[6].getText())) {
            if (buttons[2].getText().equals("X")) {
                label.setText("X Wins!");
                return true;
            } else if (buttons[2].getText().equals("O")) {
                label.setText("O Wins!");
                return true;
            }
        }
        return false;
    }

    private boolean GameIsOver() {
        if (NOfMoves >= 9 || IsWinner()) {
            Image icon2 = new ImageIcon(System.getProperty("user.dir")+"\\src\\resources\\icon.png").getImage().getScaledInstance(50, 50, 100);
            String[] s = {"Play Again", "Exit", "Main Menu"};
            int response = JOptionPane.showOptionDialog(null, "Do you want to play again?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(icon2), s, 0);
            if (response == 0) {
                ClearBoard();
            } else if(response == 1){
                System.exit(0);
            }
            else{
                this.setVisible(false);
                mainmenu.setVisible(true);
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (NOfMoves >= 9 && !IsWinner()) {
            label.setText("Draw!");
            for (JButton b : buttons) {
                b.setEnabled(false);
            }
        }
        if (GameIsOver()) {
            for (JButton b : buttons) {
                b.setEnabled(false);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public static void toggleFullScreen(JFrame frame) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            frame.setExtendedState(JFrame.NORMAL);
        }
    }
}
