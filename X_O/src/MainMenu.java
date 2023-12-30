import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainMenu extends JFrame implements MouseListener, KeyListener, ActionListener {
    private JButton start, settings, exit;
    private JPanel topPanel, middlePanel;


    private final int buttonSpacing = 50;
    private Color backgroundColor = new Color(1, 2, 64);
    private final Image icon = new ImageIcon("F:\\X_O-Java\\X_O\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);

    MainMenu() throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        topPanel = new JPanel(new GridLayout(1, 1));
        middlePanel = new JPanel(new GridLayout(3, 1));
        JLabel welcome = new JLabel("Welcome " + NameMenu.getUsername() + "!");
        welcome.setHorizontalAlignment(0);
        welcome.setBorder(new EmptyBorder(50, 50, 0, 50));
        welcome.setFont(new Font("Dubai", Font.BOLD, 26));
        welcome.setForeground(Color.LIGHT_GRAY);


        topPanel.add(welcome);


        start = new JButton("Start");
        settings = new JButton("Settings");
        exit = new JButton("Exit");
        start.setPreferredSize(new Dimension(150, 50));
        settings.setPreferredSize(new Dimension(100, 50));
        exit.setPreferredSize(new Dimension(100, 50));
        start.setBackground(Color.white);
        settings.setBackground(Color.white);
        exit.setBackground(Color.white);
        exit.addActionListener(this);
        start.addMouseListener(this);
        settings.addActionListener(this);
        settings.addMouseListener(this);
        exit.addMouseListener(this);
        Image starticon = new ImageIcon("F:\\X_O-Java\\X_O\\src\\resources\\icon.png").getImage().getScaledInstance(40,40,100);
        start.setIcon(new ImageIcon(starticon));
        start.setIconTextGap(20);

        JPanel startPanel = createButtonPanel(start, buttonSpacing);
        JPanel settingsPanel = createButtonPanel(settings, buttonSpacing);
        JPanel exitPanel = createButtonPanel(exit, buttonSpacing);
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
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(350, 600));
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setTitle("X_O");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        MusicPlayer.playmusic("F:\\X_O-Java\\X_O\\src\\resources\\music2.wav");
        MusicPlayer.waitForPlayback();
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
        if(e.getSource() == start){
            start.setBackground(new Color(105, 186, 240));
        }
        else if(e.getSource() == settings){
            settings.setBackground(new Color(105, 186, 240));
        }
        else if(e.getSource() == exit){
            exit.setBackground(new Color(105, 186, 240));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == start){
            start.setBackground(Color.white);
        }
        else if(e.getSource() == settings){
            settings.setBackground(Color.white);
        }
        else if(e.getSource() == exit){
            exit.setBackground(Color.white);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            Image icon2 = new ImageIcon("F:\\X_O-Java\\X_O\\src\\resources\\icon.png").getImage().getScaledInstance(50, 50, 100);
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(icon2));
            if (response == 0) {
                this.dispose();
                System.exit(0);
            }
        }
        else if(e.getSource()==settings){
            new SettingsMenu(this);
            this.setVisible(false);
        }
    }
}
