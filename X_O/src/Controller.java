import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

/**
 * Controller class manages the interaction between the game components, menus, and the AI player.
 *
 * @author Youssef Tarek
 * @version 2.0
 */
public class Controller {

    /**
     * The {@link MainMenu} object that will be controlled.
     *
     * @see MainMenu
     */
    private static MainMenu mainMenu;
    /**
     * The {@link SettingsMenu} object that will be controlled.
     *
     * @see SettingsMenu
     */
    private static SettingsMenu settingsMenu;

    /**
     * {@link String} variable that stores the path to the default song.
     */
    private final String defualtSong = System.getProperty("user.dir") + "\\src\\resources\\music.wav";
    /**
     * The {@link AIPlayer} object that will be controlled.
     *
     * @see AIPlayer
     */
    private static AIPlayer player;
    /**
     * The {@link XOBoard} object that will be controlled.
     *
     * @see XOBoard
     */
    private static XOBoard xoBoard;

    /**
     * Constructor for the Controller class.
     *
     * @param mainMenu,     {@link MainMenu} object which represents the main menu of the game.
     * @param settingsMenu, {@link SettingsMenu} object which represents the settings menu of the game.
     * @param xoBoard,      {@link XOBoard} object which is the game itself with the board and the logic behind it.
     * @throws UnsupportedAudioFileException If the user chooses unsupported audio file extension.
     * @throws LineUnavailableException      If the user opens a file that doesn't exist or is being used by another app.
     * @throws IOException                   If the user has an any kind of issues opening the file.
     * @see java.awt.event.ActionListener
     * @see java.awt.event.WindowListener
     * @see WindowAdapter
     * @see JButton
     * @see JFrame
     * @see MainMenu
     * @see SettingsMenu
     * @see XOBoard
     * @see MusicPlayer
     * @see JFrame#addWindowListener(WindowListener)
     * @see JButton#addActionListener(ActionListener)
     * @see JOptionPane#showConfirmDialog(Component, Object)
     */
    Controller(MainMenu mainMenu, SettingsMenu settingsMenu, XOBoard xoBoard) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // Initialize the static variables.
        Controller.mainMenu = mainMenu;
        Controller.settingsMenu = settingsMenu;
        Controller.xoBoard = null;
        // Start the music.
        MusicPlayer.playmusic(Controller.mainMenu.getUsername().equals("Nomusic") ? null : defualtSong);
        /* Use the getter for the settings button to add an Action listener to show the settings menu and hide the main
         * menu.
         */
        mainMenu.getSettings().addActionListener(e -> {
            Controller.mainMenu.setVisible(false);
            Controller.settingsMenu.setVisible(true);
        });
        /*
         * Add window listener with a new window adapter to override the window closing to show the exit prompt.
         */
        mainMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ExitPrompt();
            }
        });
        /*
         * Use getter for exit to add action listener to show the exit prompt.
         */
        mainMenu.getExit().addActionListener(e -> {
            ExitPrompt();
        });
        /*
         * Use getter for start button to add action listener to initialize the game.
         */
        mainMenu.getStart().addActionListener(e -> {
            // Initialize the xoBoard variable.
            Controller.xoBoard = new XOBoard();
            if (SettingsMenu.IsFullScreen()) toggleFullScreen(Controller.xoBoard);
            Controller.xoBoard.ChangeColor(Controller.mainMenu.getBackground());
            // Hide the main menu.
            Controller.mainMenu.setVisible(false);
            // Take response from user whether they want to play with a human or AI.
            int response = JOptionPane.showConfirmDialog(null, "Do you want to play with another player?", "Choose player or AI", JOptionPane.YES_NO_OPTION);
            // Check the response.
            if (response == 0) {
                player = null;
                Controller.xoBoard.setVisible(true);
            } else if (response == 1) {
                // If they chose the AI, initialize the AI Player and set the label to "O's Turn" and get the AI move.
                Controller.xoBoard.setVisible(true);
                player = new AIPlayer();
                Controller.xoBoard.getLabel().setText("O's Turn");
                makeAIMove();
            } else {
                // If the user didn't choose anything just return back to the main menu.
                Controller.mainMenu.setVisible(true);
            }
        });
        /*
         * Use the getter for the back button in the settings menu to go back to the main menu.
         */
        settingsMenu.getBack().addActionListener(e -> {
            Controller.settingsMenu.setVisible(false);
            Controller.mainMenu.setVisible(true);
        });
        /*
         * Add window listener to the settings menu with a new window adapter to override the window closing so it
         * would go back to the main menu.
         */
        settingsMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Controller.settingsMenu.setVisible(false);
                Controller.mainMenu.setVisible(true);
            }
        });
        /*
         * Use the getter to get the fullscreen button in settings menu to add an action listener to it to enable
         * fullscreen for all the other classes.
         */
        settingsMenu.getFullscreen().addActionListener(e -> {
            toggleFullScreen(Controller.settingsMenu);
            toggleFullScreen(Controller.mainMenu);
            // Set the text of the fullscreen button.
            Controller.settingsMenu.getFullscreen().setText((SettingsMenu.IsFullScreen()) ? "Fullscreen ON" : "Fullscreen OFF");
        });
        /*
         * Use the getter for the music button to enable/disable the music and that depends on the value of the isPlaying
         * boolean in the MusicPlayer class.
         */
        settingsMenu.getMusic().addActionListener(e -> {
            if (MusicPlayer.isPlaying()) {
                MusicPlayer.pausePlayback();
                Controller.settingsMenu.getMusic().setText("Music OFF");
            } else {
                MusicPlayer.resumePlayback();
                Controller.settingsMenu.getMusic().setText("Music ON");
            }
        });
        /*
         * Use the getter for the change music button to add action listener to give the user the option to select a new
         * song to play.
         */
        settingsMenu.getChangeMusic().addActionListener(e -> {
            try {
                openNewMusic();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        /*
         * Use the getter for the change color button to add action listener to give the user the option to select a new
         * color for the menus and the board.
         */
        settingsMenu.getChangeColor().addActionListener(e -> {
            Color choosencolor = JColorChooser.showDialog(Controller.settingsMenu, "Choose new color", Color.BLACK);
            if (choosencolor != null) {
                Controller.settingsMenu.ChangeColor(choosencolor);
                Controller.mainMenu.ChangeColor(choosencolor);
            } else {
                JOptionPane.showMessageDialog(null, "You didn't choose a color!", "No color choosen", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    /**
     * <p>Toggles full-screen mode for the given {@link JFrame} by storing the local graphics environment in a variable
     * called {@code graphicsEnvironment} by using the {@link GraphicsEnvironment#getLocalGraphicsEnvironment()} method</p>
     * <p>Then creates new variable called {@code graphicsDevice} and stores the default screen size in it by using
     * {@link GraphicsEnvironment#getDefaultScreenDevice()} method.</p>
     * <p>Then checks if the given {@code frame} is maximized or not by using the method {@link JFrame#getExtendedState()}
     * and checks if its not equal to {@link JFrame#MAXIMIZED_BOTH}, if it isn't then it sets the extended state to
     * {@link JFrame#MAXIMIZED_BOTH} by using the {@link JFrame#setExtendedState(int)}} method, else it makes it its size
     * size by using the same method but with parameter {@link JFrame#NORMAL}</p>
     *
     * @param frame takes {@link JFrame} to make the size as fullscreen.
     * @see JFrame
     * @see GraphicsEnvironment
     * @see GraphicsDevice
     */
    private static void toggleFullScreen(JFrame frame) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            SettingsMenu.setIsFullScreen(true);
        } else {
            frame.setExtendedState(JFrame.NORMAL);
            SettingsMenu.setIsFullScreen(false);
        }
    }
    /**
     * <p>Displays an exit prompt using {@link JOptionPane#showConfirmDialog(Component, Object)} and stores the return in an
     * int variable called {@code response}.
     * </p>
     * <p>If {@code response == 0} then it exits the code by using {@link System#exit(int)}.</p>
     *
     * @see JOptionPane#showConfirmDialog(Component, Object) showConfirmDialog()
     */
    public static void ExitPrompt() {
        Image icon2 = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(50, 50, 100);
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(icon2));
        if (response == 0) {
            System.exit(0);
        }
    }

    /**
     * Gets the {@link Controller#mainMenu} instance.
     *
     * @return the static {@link Controller#mainMenu} object.
     */
    public static MainMenu getMainMenu() {
        return Controller.mainMenu;
    }

    /**
     * Opens a file chooser dialog to select a new music file using {@link JFileChooser}.
     * Then sets the file filter {@link JFileChooser#setFileFilter(FileFilter)} to make the user only choose wav files.
     * <p>Then sets the current directory to the directory of the source files by using {@link JFileChooser
     * #setCurrentDirectory(File)}.</p>
     * <p>Then check if the user cancelled or not by using {@link JFileChooser#showDialog(Component, String)} which returns
     * {@value JFileChooser#APPROVE_OPTION} if the user chose a file and didn't cancel.</p>
     * <p>
     * Then checks if the user chose a WAV file or not by using {@link JFileChooser#getSelectedFile()} and storing it in
     * a new {@link File} object called {@code selectedSong} and then calls {@link File#getAbsolutePath()} method to get
     * the absolute path of the file, then uses {@link String#substring(int, int)} to get the extension.</p>
     *
     * @throws UnsupportedAudioFileException If the user chooses unsupported audio file extension.
     * @throws LineUnavailableException      If the user opens a file that doesn't exist or is being used by another app.
     * @throws IOException                   If the user has an any kind of issues opening the file.
     * @see JFileChooser
     * @see MusicPlayer
     * @see System#getProperty(String)
     */
    private void openNewMusic() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Wav files", "wav"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\src\\resources\\"));
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setBackground(Color.white);
        fileChooser.setFocusable(false);
        fileChooser.setDialogTitle("Choose a new song.");
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedSong = fileChooser.getSelectedFile();
            if (!selectedSong.getAbsolutePath().substring(selectedSong.getAbsolutePath().lastIndexOf('.') + 1).equals("wav")) {
                JOptionPane.showMessageDialog(null, "You need to choose a WAV file!", "Unsupported file chosen", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MusicPlayer.stopPlayback();
                MusicPlayer.playmusic(selectedSong.getAbsolutePath());
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>The method used when the {@link Controller#player} is required to make a move.</p>
     * <p>Starts by calling the {@link AIPlayer#makeMove()} and stores the return in an int array called {@code aiMove}.
     * </p>
     * <p>Then calls the method {@link XOBoard#DisableAllButtons()} to disable the buttons until the move is registered.
     * </p>
     * <p>Then calls the {@link XOBoard#getButtons()} method and uses index 0 and index 1 in the {@code aiMove} array to
     * get the right button in the 2d array of buttons in {@link XOBoard} by using row {@code i} and column {@code j} to
     * set the text as "X" by using the {@link JButton#setText(String)} method.</p>
     * <p>Then disables the same button by using the {@link JButton#setEnabled(boolean)} method.</p>
     * <p>Then enables the buttons again by calling the {@link XOBoard#EnableAllButtons()} method.</p>
     * <p>Then added one to the {@code NOMoves} property in the {@link XOBoard} class by using the
     * {@link XOBoard#setNOfMoves(int)} method and in side of it, it uses {@link XOBoard#getNOfMoves()} and adds one
     * to it.</p>
     */
    public static void makeAIMove() {
        int[] aiMove = Controller.player.makeMove();
        Controller.xoBoard.DisableAllButtons();
        Controller.xoBoard.getButtons()[aiMove[0]][aiMove[1]].setText("X");
        Controller.xoBoard.getButtons()[aiMove[0]][aiMove[1]].setEnabled(false);
        Controller.xoBoard.EnableAllButtons();
        Controller.xoBoard.setNOfMoves(Controller.xoBoard.getNOfMoves() + 1);
    }

    /**
     * Gets the static player object from the {@link Controller}.
     *
     * @return {@link Controller#player}.
     */
    public static AIPlayer getPlayer() {
        return Controller.player;
    }

    /**
     * Sets the {@link Controller#player} object.
     *
     * @param player takes {@link AIPlayer} object.
     */
    public static void setPlayer(AIPlayer player) {
        Controller.player = player;
    }
}
