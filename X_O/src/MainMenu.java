import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * Main Menu class which is the main menu for the game which routes to the game or the settings menu.
 *
 * @author Youssef Tarek
 * @version 2.0
 * @see JFrame
 * @see MouseListener
 * @see ActionListener
 */

public class MainMenu extends JFrame implements MouseListener, ActionListener {

    /**
     * <p>Private final instance of the {@link JButton} in the main menu.</p>
     *
     * @see JButton
     */
    private final JButton start, settings, exit;
    /**
     * Private final instance of the {@link JPanel} in the main menu.
     *
     * @see JPanel
     */
    private final JPanel topPanel, middlePanel, startPanel, settingsPanel, exitPanel;
    /**
     * Private final instance {@link Integer} value representing the space between buttons.
     */
    private final int BUTTON_SPACING = 50;
    /**
     * Private final instance {@link Color} representing the background color for the main menu.
     *
     * @see Color#Color(int, int, int)
     */
    private final Color backgroundColor = new Color(1, 2, 64);
    /**
     * Private final {@link Image} representing the icon of the main menu.
     *
     * @see Image
     * @see ImageIcon
     * @see ImageIcon#ImageIcon(String)
     */
    private final Image icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(300, 300, 900);

    /**
     * Private static {@link String} representing the name of the user taken from the {@link Main} class.
     */
    private static String Username;

    /**
     * <p>The constructor for the {@link MainMenu} class.</p>
     *
     * @param name The name of the user.
     * @see GridLayout
     * @see JLabel
     * @see BorderLayout
     * @see Toolkit
     * @see Toolkit#getDefaultToolkit()
     * @see EmptyBorder#EmptyBorder(int, int, int, int)
     */
    MainMenu(String name) {
        // Initialize the username, top panel and middle panel variables.
        Username = name;
        // Set the layout for both the panel to GridLayout.
        topPanel = new JPanel(new GridLayout(1, 1));
        middlePanel = new JPanel(new GridLayout(3, 1));
        // Make a new JLabel that welcomes the user.
        JLabel welcome = new JLabel("Welcome " + ((getUsername().equals("Zura")) ? "Sharmoota" : getUsername()) + "!");
        // Center the JLabel.
        welcome.setHorizontalAlignment(0);
        // Make spacing around the JLabel by creating a new EmptyBorder.
        welcome.setBorder(new EmptyBorder(50, 50, 0, 50));
        // Set the font for the label.
        welcome.setFont(new Font("Dubai", Font.BOLD, 26));
        // Set the color for the label.
        welcome.setForeground(Color.LIGHT_GRAY);

        // Add the label to the top panel.
        topPanel.add(welcome);

        // Initialize the buttons.
        start = new JButton("Start");
        settings = new JButton("Settings");
        exit = new JButton("Exit");
        // Set the size of the buttons.
        start.setPreferredSize(new Dimension(150, 50));
        settings.setPreferredSize(new Dimension(150, 50));
        exit.setPreferredSize(new Dimension(150, 50));
        // Set the background color of the buttons.
        start.setBackground(Color.white);
        settings.setBackground(Color.white);
        exit.setBackground(Color.white);
        // Add the listeners to the buttons.
        exit.addActionListener(this);
        start.addMouseListener(this);
        start.addActionListener(this);
        settings.addActionListener(this);
        settings.addMouseListener(this);
        exit.addMouseListener(this);
        // Create new ImageIcon objects for each button to add icons to them.
        Image starticon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(40, 40, 100);
        Image settingsicon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\settings.png").getImage().getScaledInstance(40, 40, 100);
        Image exiticon = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\exit.png").getImage().getScaledInstance(60, 60, 100);
        // Set the icon for each button.
        settings.setIcon(new ImageIcon(settingsicon));
        start.setIcon(new ImageIcon(starticon));
        exit.setIcon(new ImageIcon(exiticon));
        // Set the gap between the Text and the Icon
        settings.setIconTextGap(20);
        start.setIconTextGap(20);
        exit.setIconTextGap(20);
        // Use the createButtonPanel function to create each panel for each button
        startPanel = createButtonPanel(start);
        settingsPanel = createButtonPanel(settings);
        exitPanel = createButtonPanel(exit);
        // Set the color for each panel.
        startPanel.setBackground(new Color(2, 3, 74));
        settingsPanel.setBackground(new Color(2, 3, 74));
        exitPanel.setBackground(new Color(2, 3, 74));
        setBackground(backgroundColor);

        // Disable the focusable for all buttons.
        start.setFocusable(false);
        settings.setFocusable(false);
        exit.setFocusable(false);

        // Add the button panels to the middle panel.
        middlePanel.add(startPanel);
        middlePanel.add(settingsPanel);
        middlePanel.add(exitPanel);
        // Add spacing for the middle panel using an EmptyBorder.
        middlePanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        // Set the background color for both the top panel and the bottom panel.
        middlePanel.setBackground(backgroundColor);
        topPanel.setBackground(backgroundColor);
        // Get the current screen size of the user.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set the layout of the main menu to a border layout.
        this.setLayout(new BorderLayout());
        // Set the icon of the main manu.
        this.setIconImage(new ImageIcon(icon).getImage());
        // Add the panel with their respective positions.
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        // Set the default close operation to DO_NOTHING_ON_CLOSE because it will be overriden in the Controller class.
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // Set the size for the main menu.
        this.setSize(new Dimension(350, 600));
        // Set the location to the center of the screen.
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        // Set the title of the main menu.
        this.setTitle("X O Menu");
        // Make it not resizable.
        this.setResizable(false);
        // Pack everything.
        this.pack();
        // Show the main menu.
        this.setVisible(true);

    }

    /**
     * <p>The function that creates the panels for the {@link MainMenu#start},{@link MainMenu#settings} and
     * {@link MainMenu#exit} buttons.</p>
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
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        return panel;
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
        if (e.getSource() == start) {
            start.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == settings) {
            settings.setBackground(new Color(105, 186, 240));
        } else if (e.getSource() == exit) {
            exit.setBackground(new Color(105, 186, 240));
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

    /**
     * <p>Getter for the {@link MainMenu#Username} variable which has the name of the user in it.</p>
     * <p>The function changes the first char of the string to a upper case by using {@link Character#isLowerCase(char)}
     * to check if its lower case and if it is it converts it to uppercase using the {@link Character#toUpperCase(char)}
     * method then add the first char back to the {@link MainMenu#Username} variable.</p>
     *
     * @return {@link MainMenu#Username}
     */
    public String getUsername() {
        if (Username == null) return "";
        char first = Username.charAt(0);
        if (Character.isLowerCase(first)) {
            first = Character.toUpperCase(first);
            Username = first + Username.substring(1);
        }
        return Username;
    }

    /**
     * <p>Checks if the username is valid or not.</p>
     * <p>A valid username means its not too long (longer than 15 chars) or has any non alphabetical characters
     * or empty.</p>
     *<p>Function uses regex pattern to check for non alphabetical characters.</p>
     * @see Pattern
     * @param s The username to be checked
     * @return {@code true} if the username is valid else returns false.
     */
    public static boolean ValidUsername(String s) {
        if (s == null) System.exit(0);
        return (s.length() < 15 && !Pattern.compile("[^a-zA-Z]").matcher(s).find() && !s.isEmpty());
    }

    /**
     * <p>Getter for the settings {@link JButton}</p>
     * @return {@link MainMenu#settings}
     */
    public JButton getSettings() {
        return settings;
    }
    /**
     * <p>Getter for the start {@link JButton}</p>
     * @return {@link MainMenu#start}
     */
    public JButton getStart() {
        return start;
    }
    /**
     * <p>Getter for the exit {@link JButton}</p>
     * @return {@link MainMenu#exit}
     */
    public JButton getExit() {
        return exit;
    }

    /**
     * <p>Changes the background colors of all the panels in the {@link MainMenu} by using the {@link 
     * JPanel#setBackground(Color)} method on all panels.</p>
     * @see JPanel#setBackground(Color) 
     * @param newColor The new color of the menus.
     */
    public void ChangeColor(Color newColor) {
        setBackground(newColor);
        startPanel.setBackground(newColor);
        settingsPanel.setBackground(newColor);
        exitPanel.setBackground(newColor);
        topPanel.setBackground(newColor.darker());
        middlePanel.setBackground(newColor.darker());
    }
}
