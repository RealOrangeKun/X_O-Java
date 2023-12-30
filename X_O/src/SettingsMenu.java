import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsMenu extends JFrame implements ActionListener, MouseListener {
    private final Image icon = new ImageIcon("F:\\X_O-Java\\X_O\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);
    private JPanel middlePanel;
    private JButton music, fullscreen, back;
    private Color backgroundColor = new Color(1, 2, 64);

    private static boolean musicplaying = true;

    private JFrame mainmenu;

    private static boolean isFullScreen =false;

    SettingsMenu(JFrame menu) {
        mainmenu = menu;
        music = new JButton("Music ON");
        fullscreen = new JButton("Fullscreen OFF");
        back = new JButton("Back");
        music.setPreferredSize(new Dimension(150, 50));
        fullscreen.setPreferredSize(new Dimension(150, 50));
        back.setPreferredSize(new Dimension(150, 50));
        setButtonsSettings();

        JPanel musicpanel = createButtonPanel(music, 50);
        JPanel fullscreenpanel = createButtonPanel(fullscreen, 50);
        JPanel backpanel = createButtonPanel(back, 50);


        middlePanel = new JPanel(new GridLayout(3, 1, 50, 50));
        middlePanel.setBackground(backgroundColor);
        middlePanel.add(musicpanel);
        middlePanel.add(fullscreenpanel);
        middlePanel.add(backpanel);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new Dimension(350, 600));
        this.enableMain(menu);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setIconImage(new ImageIcon(icon).getImage());
        this.add(middlePanel, BorderLayout.CENTER);
        this.setTitle("Settings");
        this.pack();
        this.setVisible(true);
    }

    private JPanel createButtonPanel(JButton button, int spacing) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
        panel.setBackground(backgroundColor);
        return panel;
    }

    private void setButtonsSettings() {
        music.addActionListener(this);
        fullscreen.addActionListener(this);
        music.addActionListener(this);
        music.addMouseListener(this);
        fullscreen.addMouseListener(this);
        back.addMouseListener(this);
        music.setFocusable(false);
        fullscreen.setFocusable(false);
        back.setFocusable(false);
        music.setBackground(Color.white);
        fullscreen.setBackground(Color.white);
        back.setBackground(Color.white);
    }
    private static void toggleFullScreen(JFrame frame) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            isFullScreen = true;
        } else {
            frame.setExtendedState(JFrame.NORMAL);
            isFullScreen = false;
        }
    }

    private void enableMain(JFrame m) {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                m.setVisible(true);
            }
        });
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == music) {
            if (!MusicPlayer.isPlaying()) {
                MusicPlayer.resumePlayback();
            } else {
                MusicPlayer.pausePlayback();
            }
            music.setText((MusicPlayer.isPlaying()) ? "Music ON" : "Music OFF");
        }
        else if(e.getSource()==fullscreen){
            toggleFullScreen(this);
            toggleFullScreen(mainmenu);
            fullscreen.setText((isFullScreen)? "Fullscreen ON": "Fullscreen OFF");
        }
        else if(e.getSource() == back){
            this.setVisible(false);
            mainmenu.setVisible(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == music) {
            music.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == fullscreen) {
            fullscreen.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == back) {
            back.setBackground(new Color(105, 186, 240));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == music) {
            music.setBackground(Color.white);
        } else if (e.getSource() == fullscreen) {
            fullscreen.setBackground(Color.white);
        } else if (e.getSource() == back) {
            back.setBackground(Color.white);
        }
    }

    public static boolean IsFullScreen(){
        return isFullScreen;
    }
}
