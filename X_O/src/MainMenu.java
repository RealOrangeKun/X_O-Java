import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;

public class MainMenu extends JFrame implements MouseListener, KeyListener, ActionListener {
    private JButton start, settings, exit;
    private JPanel topPanel, middlePanel, startPanel, settingsPanel, exitPanel;

    private final int buttonSpacing = 50;
    private Color backgroundColor = new Color(1, 2, 64);
    private final Image icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);


    private static String Username;

    MainMenu(String name) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        Username = name;
        topPanel = new JPanel(new GridLayout(1, 1));
        middlePanel = new JPanel(new GridLayout(3, 1));
        JLabel welcome = new JLabel("Welcome " + ((getUsername().equals("Zura")) ? "Sharmoota" : getUsername()) + "!");
        welcome.setHorizontalAlignment(0);
        welcome.setBorder(new EmptyBorder(50, 50, 0, 50));
        welcome.setFont(new Font("Dubai", Font.BOLD, 26));
        welcome.setForeground(Color.LIGHT_GRAY);


        topPanel.add(welcome);


        start = new JButton("Start");
        settings = new JButton("Settings");
        exit = new JButton("Exit");
        start.setPreferredSize(new Dimension(150, 50));
        settings.setPreferredSize(new Dimension(150, 50));
        exit.setPreferredSize(new Dimension(150, 50));
        start.setBackground(Color.white);
        settings.setBackground(Color.white);
        exit.setBackground(Color.white);
        exit.addActionListener(this);
        start.addMouseListener(this);
        start.addActionListener(this);
        settings.addActionListener(this);
        settings.addMouseListener(this);
        exit.addMouseListener(this);
        Image starticon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(40, 40, 100);
        Image settingsicon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\settings.png").getImage().getScaledInstance(40, 40, 100);
        Image exiticon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\exit.png").getImage().getScaledInstance(60, 60, 100);
        settings.setIcon(new ImageIcon(settingsicon));
        start.setIcon(new ImageIcon(starticon));
        exit.setIcon(new ImageIcon(exiticon));
        settings.setIconTextGap(20);
        start.setIconTextGap(20);
        exit.setIconTextGap(20);

        startPanel = createButtonPanel(start, buttonSpacing);
        settingsPanel = createButtonPanel(settings, buttonSpacing);
        exitPanel = createButtonPanel(exit, buttonSpacing);
        startPanel.setBackground(new Color(2, 3, 74));
        settingsPanel.setBackground(new Color(2, 3, 74));
        exitPanel.setBackground(new Color(2, 3, 74));


        start.setFocusable(false);
        settings.setFocusable(false);
        exit.setFocusable(false);


        middlePanel.add(startPanel);
        middlePanel.add(settingsPanel);
        middlePanel.add(exitPanel);
        middlePanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        middlePanel.setBackground(backgroundColor);
        topPanel.setBackground(backgroundColor);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon(icon).getImage());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(new Dimension(350, 600));
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setTitle("X O Menu");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

    }

    private JPanel createButtonPanel(JButton button, int spacing) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
        return panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == start) {
            start.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == settings) {
            settings.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == exit) {
            exit.setBackground(new Color(105, 186, 240));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == start) {
            start.setBackground(Color.white);
        } else if (e.getSource() == settings) {
            settings.setBackground(Color.white);
        } else if (e.getSource() == exit) {
            exit.setBackground(Color.white);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public String getUsername() {
        if (Username == null) return "";
        char first = Username.charAt(0);
        if (Character.isLowerCase(first)) {
            first = Character.toUpperCase(first);
            Username = first + Username.substring(1);
        }
        return Username;
    }

    public static boolean ValidUsername(String s) {
        if (s == null) System.exit(0);
        return (s.length() < 15 && !Pattern.compile("[^a-zA-Z]").matcher(s).find() && !s.isEmpty());
    }

    public JButton getSettings() {
        return settings;
    }

    public JButton getStart() {
        return start;
    }

    public JButton getExit() {
        return exit;
    }

    public void ChangeColor(Color newcolor) {
        startPanel.setBackground(newcolor);
        settingsPanel.setBackground(newcolor);
        exitPanel.setBackground(newcolor);
        topPanel.setBackground(newcolor.darker());
        middlePanel.setBackground(newcolor.darker());
    }
}
