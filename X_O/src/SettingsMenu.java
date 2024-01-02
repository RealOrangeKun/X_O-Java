import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsMenu extends JFrame implements ActionListener, MouseListener {
    private final Image icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);
    private JPanel middlePanel;
    private JButton music, fullscreen, back, changeMusic, changeColor;
    private Color backgroundColor = new Color(1, 2, 64);

    private final int BUTTON_SPACING = 25;

    private JPanel musicpanel,fullscreenpanel, backpanel, changemusicpanel, changecolorpanel;

    private static boolean isFullScreen = false;

    SettingsMenu() {
        music = new JButton("Music ON");
        fullscreen = new JButton("Fullscreen OFF");
        back = new JButton("Back");
        changeMusic = new JButton("Change Music");
        changeColor = new JButton("Change Color");
        music.setPreferredSize(new Dimension(150, 50));
        fullscreen.setPreferredSize(new Dimension(150, 50));
        back.setPreferredSize(new Dimension(150, 50));
        changeMusic.setPreferredSize(new Dimension(150, 50));
        changeColor.setPreferredSize(new Dimension(150, 50));
        changeColor.addActionListener(this);
        changeColor.setFocusable(false);
        changeMusic.addActionListener(this);
        changeMusic.setFocusable(false);
        music.addActionListener(this);
        fullscreen.addActionListener(this);
        music.addActionListener(this);
        music.addMouseListener(this);
        fullscreen.addMouseListener(this);
        back.addActionListener(this);
        back.addMouseListener(this);
        changeMusic.addMouseListener(this);
        changeColor.addMouseListener(this);
        music.setFocusable(false);
        fullscreen.setFocusable(false);
        back.setFocusable(false);
        changeColor.setBackground(Color.white);
        changeMusic.setBackground(Color.white);
        music.setBackground(Color.white);
        fullscreen.setBackground(Color.white);
        back.setBackground(Color.white);

        musicpanel = createButtonPanel(music, BUTTON_SPACING);
        fullscreenpanel = createButtonPanel(fullscreen, BUTTON_SPACING);
        backpanel = createButtonPanel(back, BUTTON_SPACING);
        changemusicpanel = createButtonPanel(changeMusic, BUTTON_SPACING);
        changecolorpanel = createButtonPanel(changeColor, BUTTON_SPACING);


        middlePanel = new JPanel(new GridLayout(5, 1, 50, 0));
        middlePanel.setBackground(backgroundColor);
        middlePanel.add(musicpanel);
        middlePanel.add(fullscreenpanel);
        middlePanel.add(changemusicpanel);
        middlePanel.add(changecolorpanel);
        middlePanel.add(backpanel);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(new Dimension(350, 600));
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setIconImage(new ImageIcon(icon).getImage());
        this.add(middlePanel, BorderLayout.CENTER);
        this.setTitle("Settings");
        this.pack();
    }

    private JPanel createButtonPanel(JButton button, int spacing) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
        panel.setBackground(backgroundColor);
        return panel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

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
        if (e.getSource() == music) {
            music.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == fullscreen) {
            fullscreen.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == back) {
            back.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == changeMusic) {
            changeMusic.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == changeColor) {
            changeColor.setBackground(new Color(105, 186, 240));
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
        } else if (e.getSource() == changeMusic) {
            changeMusic.setBackground(Color.white);
        } else if (e.getSource() == changeColor) {
            changeColor.setBackground(Color.white);
        }
    }

    public static boolean IsFullScreen() {
        return isFullScreen;
    }

    public JButton getBack() {
        return back;
    }

    public JButton getFullscreen() {
        return fullscreen;
    }

    public static void setIsFullScreen(boolean isFullScreen) {
        SettingsMenu.isFullScreen = isFullScreen;
    }

    public JButton getMusic() {
        return music;
    }

    public JButton getChangeMusic() {
        return changeMusic;
    }

    public JButton getChangeColor() {
        return changeColor;
    }

    public void ChangeColor(Color newcolor) {
        middlePanel.setBackground(newcolor);
        musicpanel.setBackground(newcolor);
        fullscreenpanel.setBackground(newcolor);
        changecolorpanel.setBackground(newcolor);
        changemusicpanel.setBackground(newcolor);
        musicpanel.setBackground(newcolor);
        backpanel.setBackground(newcolor);
    }
}
