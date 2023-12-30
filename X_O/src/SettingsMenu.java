import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsMenu extends JFrame implements ActionListener, MouseListener {
    private final Image icon = new ImageIcon("F:\\X_O-Java\\X_O\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);
    private JPanel middlePanel;
    private JButton music, fullscreen, color;
    private Color backgroundColor = new Color(1, 2, 64);

    private static boolean musicplaying = true;

    SettingsMenu(JFrame menu) {
        music = new JButton((MusicPlayer.isPlaying()) ? "Music ON" : "Music OFF");
        fullscreen = new JButton("Fullscreen OFF");
        color = new JButton("Change Color");
        music.setPreferredSize(new Dimension(150, 50));
        fullscreen.setPreferredSize(new Dimension(150, 50));
        color.setPreferredSize(new Dimension(150, 50));
        setButtonsSettings();

        JPanel musicpanel = createButtonPanel(music, 50);
        JPanel fullscreenpanel = createButtonPanel(fullscreen, 50);
        JPanel colorpanel = createButtonPanel(color, 50);


        middlePanel = new JPanel(new GridLayout(3, 1, 50, 50));
        middlePanel.setBackground(backgroundColor);
        middlePanel.add(musicpanel);
        middlePanel.add(fullscreenpanel);
        middlePanel.add(colorpanel);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new Dimension(350, 600));
        this.enableMain(menu);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        color.addMouseListener(this);
        music.setFocusable(false);
        fullscreen.setFocusable(false);
        color.setFocusable(false);
        music.setBackground(Color.white);
        fullscreen.setBackground(Color.white);
        color.setBackground(Color.white);
    }

    private void enableMain(JFrame m) {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                m.setVisible(true);
            }
        });
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
        } else if (e.getSource() == color) {
            color.setBackground(new Color(105, 186, 240));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == music) {
            music.setBackground(Color.white);
        } else if (e.getSource() == fullscreen) {
            fullscreen.setBackground(Color.white);
        } else if (e.getSource() == color) {
            color.setBackground(Color.white);
        }
    }
}