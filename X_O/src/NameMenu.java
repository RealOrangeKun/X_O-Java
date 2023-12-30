import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class NameMenu extends JDialog implements MouseListener, ActionListener, KeyListener {
    private JTextField namefield;

    private JButton submit;

    private JPanel topPanel, bottomPanel;
    private boolean button_pressed = false;

    private static String Username;

    NameMenu(Frame parent) {
        super(parent, true);
        Image icon = new ImageIcon(System.getProperty("user.dir")+"\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(600, 600);
        this.setTitle("Please enter your name");
        this.setIconImage(new ImageIcon(icon).getImage());
        this.addMouseListener(this);
        this.addKeyListener(this);

        topPanel = new JPanel(new GridLayout());
        JLabel top_panel_label = new JLabel("Please enter your name in the text field");
        topPanel.add(top_panel_label);
        top_panel_label.setVerticalAlignment(0);
        top_panel_label.setHorizontalAlignment(0);
        top_panel_label.setFont(new Font("Dubai", Font.BOLD, 26));
        topPanel.setPreferredSize(new Dimension(100, 300));
        submit = new JButton("Submit");
        submit.setPreferredSize(new Dimension(100, 200));
        submit.setFocusable(false);
        submit.setBackground(Color.LIGHT_GRAY);
        submit.setBorder(new StrokeBorder(new BasicStroke(1)));
        submit.setForeground(Color.white);
        submit.addMouseListener(this);
        submit.addActionListener(this);

        namefield = new JTextField("Please enter your name here");
        namefield.setLayout(new GridLayout());
        namefield.setFont(new Font("Dubai", Font.BOLD, 26));
        namefield.setForeground(Color.LIGHT_GRAY);
        namefield.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(0, 0, 0, 20)));
        namefield.setHorizontalAlignment(0);
        namefield.setCaretPosition(namefield.getText().length());
        namefield.addMouseListener(this);
        namefield.setPreferredSize(new Dimension(this.getWidth() - submit.getWidth(), 150));
        namefield.setEnabled(false);
        namefield.addKeyListener(this);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(namefield, BorderLayout.CENTER);
        bottomPanel.add(submit, BorderLayout.EAST);


        this.add(bottomPanel, BorderLayout.NORTH);
        this.add(topPanel, BorderLayout.CENTER);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setVisible(true);
    }

    public boolean Userinput() {
        return button_pressed && !(Objects.equals(namefield.getText(), "Please enter your name here") ||
                Objects.equals(namefield.getText(), ""));
    }

    public static String getUsername() {
        if (Username == null) return "";
        char first = Username.charAt(0);
        if(Character.isLowerCase(first)){
            first = Character.toUpperCase(first);
            Username = first + Username.substring(1);
        }
        return Username;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this) {
            namefield.setEnabled(false);
            namefield.setForeground(Color.lightGray);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == namefield) {
            namefield.setEnabled(true);
            if (Objects.equals(namefield.getText(), "Please enter your name here")) namefield.setText("");
            namefield.setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setBorder(new LineBorder(Color.BLACK));
            submit.setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setBorder(new StrokeBorder(new BasicStroke(1)));
            submit.setForeground(Color.white);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            Username = namefield.getText();
            namefield.setEnabled(false);
            button_pressed = true;
            dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10 && e.getSource() == namefield) {
            Username = namefield.getText();
            namefield.setEnabled(false);
            button_pressed = true;
            dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}