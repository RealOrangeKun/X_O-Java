import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * <p>Settings menu class which is the settings menu for the game which changes ths settings for the game like the fullscreen
 * ,the color, the music, turning music on/ff.</p>
 *
 * @author Youssef Tarek
 * @version 2.0
 * @see JFrame
 * @see MouseListener
 * @see ActionListener
 */
public class SettingsMenu extends JFrame implements ActionListener, MouseListener {
    /**
     * Private final {@link Image} representing the icon of the settings menu.
     *
     * @see Image
     * @see ImageIcon
     * @see ImageIcon#ImageIcon(String)
     */
    private final Image icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);
    /**
     * Private final {@link JPanel} instance of the middle panel in the SettingMenu.
     *
     * @see JPanel
     */
    private final JPanel middlePanel;
    /**
     * <p>Private final instances of the {@link JButton}s in the settings menu.</p>
     *
     * @see JButton
     */
    private final JButton music, fullscreen, back, changeMusic, changeColor;
    /**
     * Private final instance {@link Color} representing the background color for the settings menu.
     *
     * @see Color#Color(int, int, int)
     */
    private final Color backgroundColor = new Color(1, 2, 64);
    /**
     * Private final instance {@link Integer} value representing the space between buttons.
     */
    private final int BUTTON_SPACING = 25;
    /**
     * Private final instances of the {@link JPanel}s in the settings menu.
     *
     * @see JPanel
     */
    private final JPanel musicpanel, fullscreenpanel, backpanel, changemusicpanel, changecolorpanel;
    /**
     * Private final boolean value to store if the fullscreen option is on or off.
     */
    private static boolean isFullScreen = false;

    /**
     * <p>Creates a new settings menu with the buttons and the panels.</p>
     *
     * @see GridLayout#GridLayout(int, int, int, int)
     * @see SettingsMenu#createButtonPanel(JButton)
     * @see Toolkit#getDefaultToolkit()
     * @see Dimension
     * @see BorderLayout
     * @see JFrame#pack()
     */
    SettingsMenu() {
        // Initialize the main buttons.
        music = new JButton("Music ON");
        fullscreen = new JButton("Fullscreen OFF");
        back = new JButton("Back");
        changeMusic = new JButton("Change Music");
        changeColor = new JButton("Change Color");
        // Set the size of the buttons.
        music.setPreferredSize(new Dimension(150, 50));
        fullscreen.setPreferredSize(new Dimension(150, 50));
        back.setPreferredSize(new Dimension(150, 50));
        changeMusic.setPreferredSize(new Dimension(150, 50));
        changeColor.setPreferredSize(new Dimension(150, 50));
        // Add the action listeners and set the focusable.
        changeColor.addActionListener(this);
        changeColor.setFocusable(false);
        changeMusic.addActionListener(this);
        changeMusic.setFocusable(false);
        music.addActionListener(this);
        fullscreen.addActionListener(this);
        music.addActionListener(this);
        // Add the mouse listeners.
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
        // Create the panels for each button.
        musicpanel = createButtonPanel(music);
        fullscreenpanel = createButtonPanel(fullscreen);
        backpanel = createButtonPanel(back);
        changemusicpanel = createButtonPanel(changeMusic);
        changecolorpanel = createButtonPanel(changeColor);

        // Initialize the middle panel with a Grid Layout with a number of 5 rows 1 Cols and a horizontal gap of 50.
        middlePanel = new JPanel(new GridLayout(5, 1, 50, 0));
        // Set the background color.
        middlePanel.setBackground(backgroundColor);
        // Add the panels with the button into the middle panel.
        middlePanel.add(musicpanel);
        middlePanel.add(fullscreenpanel);
        middlePanel.add(changemusicpanel);
        middlePanel.add(changecolorpanel);
        middlePanel.add(backpanel);

        // Get the screen size and store it in a Dimension variable.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Sets the size of the settings menu.
        this.setSize(new Dimension(350, 600));
        // Sets the layout of the settings menu to a Border Layout.
        this.setLayout(new BorderLayout());
        // Disables the resize option for the user.
        this.setResizable(false);
        // Sets the location to the center of the screen.
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        // Sets the icon of the settings menu's window.
        this.setIconImage(new ImageIcon(icon).getImage());
        // Adds the middle panel to the settings menu in the center position.
        this.add(middlePanel, BorderLayout.CENTER);
        // Sets the title for the window to "Settings".
        this.setTitle("Settings");
        // Packs everything.
        this.pack();
    }

    /**
     * <p>The function that creates the panels for the {@link SettingsMenu#music},{@link SettingsMenu#fullscreen},
     * {@link SettingsMenu#changeMusic}, {@link SettingsMenu#changeColor}, {@link SettingsMenu#back} buttons.</p>
     * <p>Starts by creating a new {@link JPanel} with {@link BorderLayout#BorderLayout()} and then adds the {@code
     * button} parameter to the panel with the location constant {@link BorderLayout#CENTER}</p>
     * <p>Then sets the border to {@link EmptyBorder} by using {@link BorderFactory#createEmptyBorder(int, int, int, int)}</p>
     *
     * @param button The {@link JButton} to be put in the panel.
     * @return {@link JPanel} with {@link EmptyBorder#EmptyBorder(int, int, int, int)} and 50 as its spacing.
     * @see JPanel
     * @see BorderFactory
     * @see BorderLayout
     */
    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING, BUTTON_SPACING));
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

    /**
     * <p>The {@link MouseListener} to handle when the mouse touches one of the {@link JButton}s by changing the background
     * color using the {@link JButton#setBackground(Color)}.</p>
     *
     * @param e the event to be processed
     * @see JButton
     * @see MouseListener#mouseEntered(MouseEvent)
     */
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
    /**
     * <p>The {@link MouseListener} to handle when the mouse leaves one of the {@link JButton}s by changing the background
     * color using the {@link JButton#setBackground(Color)}.</p>
     *
     * @param e the event to be processed
     * @see JButton
     * @see MouseListener#mouseExited(MouseEvent)
     */
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

    /**
     * <p>Getter for the {@link SettingsMenu#isFullScreen} boolean.</p>
     * @return {@link SettingsMenu#isFullScreen}
     */
    public static boolean IsFullScreen() {
        return isFullScreen;
    }
    /**
     * <p>Getter for the {@link SettingsMenu#back} {@link JButton}.</p>
     * @return {@link SettingsMenu#back}
     */
    public JButton getBack() {
        return back;
    }
    /**
     * <p>Getter for the {@link SettingsMenu#fullscreen} {@link JButton}.</p>
     * @return {@link SettingsMenu#fullscreen}
     */
    public JButton getFullscreen() {
        return fullscreen;
    }

    public static void setIsFullScreen(boolean isFullScreen) {
        SettingsMenu.isFullScreen = isFullScreen;
    }
    /**
     * <p>Getter for the {@link SettingsMenu#fullscreen} {@link JButton}.</p>
     * @return {@link SettingsMenu#fullscreen}
     */
    public JButton getMusic() {
        return music;
    }
    /**
     * <p>Getter for the {@link SettingsMenu#changeMusic} {@link JButton}.</p>
     * @return {@link SettingsMenu#changeMusic}
     */
    public JButton getChangeMusic() {
        return changeMusic;
    }
    /**
     * <p>Getter for the {@link SettingsMenu#changeColor} {@link JButton}.</p>
     * @return {@link SettingsMenu#changeColor}
     */
    public JButton getChangeColor() {
        return changeColor;
    }
    /**
     * <p>Changes the background colors of all the panels in the {@link SettingsMenu} by using the {@link
     * JPanel#setBackground(Color)} method on all panels.</p>
     * @see JPanel#setBackground(Color)
     * @param newColor The new color of the menus.
     */
    public void ChangeColor(Color newColor) {
        middlePanel.setBackground(newColor);
        musicpanel.setBackground(newColor);
        fullscreenpanel.setBackground(newColor);
        changecolorpanel.setBackground(newColor);
        changemusicpanel.setBackground(newColor);
        musicpanel.setBackground(newColor);
        backpanel.setBackground(newColor);
    }
}
