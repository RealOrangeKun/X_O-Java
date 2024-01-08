import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/*
 * Controller class manages the interaction between the game components,
 * menus, and the AI player.
 *
 * @author Youssef Tarek
 * @version 2.0
 */
public class Controller {
    private static MainMenu mainMenu;
    private static SettingsMenu settingsMenu;
    private final String defualtSong = System.getProperty("user.dir") + "\\src\\resources\\music2.wav";

    private static AIPlayer player;
    private static boolean isAITurn;

    private static XOBoard xoBoard;

    /**
     * Constructor for the Controller class.
     *
     * @param mainMenu,     {@link MainMenu} object which represents the main menu of the game.
     * @param settingsMenu, {@link SettingsMenu} object which represents the settings menu of the game.
     * @param xoBoard,      {@link XOBoard} object which is the game itself with the board and the logic behind it
     */
    Controller(MainMenu mainMenu, SettingsMenu settingsMenu, XOBoard xoBoard) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        Controller.mainMenu = mainMenu;
        Controller.settingsMenu = settingsMenu;
        Controller.xoBoard = null;
        MusicPlayer.playmusic(Controller.mainMenu.getUsername().equals("Nomusic") ? null : defualtSong);
        mainMenu.getSettings().addActionListener(e -> {
            Controller.mainMenu.setVisible(false);
            Controller.settingsMenu.setVisible(true);
        });
        mainMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ExitPrompt();
            }
        });
        mainMenu.getExit().addActionListener(e -> {
            ExitPrompt();
        });
        mainMenu.getStart().addActionListener(e -> {
            Controller.xoBoard = new XOBoard();
            Controller.mainMenu.setVisible(false);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to play with another player?", "Choose player or AI", JOptionPane.YES_NO_OPTION);
            if (response == 0) {
                player = null;
                Controller.xoBoard.setVisible(true);
            } else if (response == 1) {
                Controller.xoBoard.setVisible(true);
                player = new AIPlayer();
                Controller.xoBoard.getLabel().setText("O's Turn");
                makeAIMove();
            } else {
                Controller.mainMenu.setVisible(true);
            }
        });
        settingsMenu.getBack().addActionListener(e -> {
            Controller.settingsMenu.setVisible(false);
            Controller.mainMenu.setVisible(true);
        });
        settingsMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Controller.settingsMenu.setVisible(false);
                Controller.mainMenu.setVisible(true);
            }
        });
        settingsMenu.getFullscreen().addActionListener(e -> {
            toggleFullScreen(Controller.settingsMenu);
            toggleFullScreen(Controller.mainMenu);
            toggleFullScreen(Controller.xoBoard);
            Controller.settingsMenu.getFullscreen().setText((SettingsMenu.IsFullScreen()) ? "Fullscreen ON" : "Fullscreen OFF");
        });
        settingsMenu.getMusic().addActionListener(e -> {
            if (MusicPlayer.isPlaying()) {
                MusicPlayer.pausePlayback();
                Controller.settingsMenu.getMusic().setText("Music OFF");
            } else {
                MusicPlayer.resumePlayback();
                Controller.settingsMenu.getMusic().setText("Music ON");
            }
        });
        settingsMenu.getChangeMusic().addActionListener(e -> {
            try {
                openNewMusic();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        settingsMenu.getChangeColor().addActionListener(e -> {
            Color choosencolor = JColorChooser.showDialog(Controller.settingsMenu, "Choose new color", Color.BLACK);
            if (choosencolor != null) {
                Controller.settingsMenu.ChangeColor(choosencolor);
                Controller.mainMenu.ChangeColor(choosencolor);
                Controller.xoBoard.ChangeColor(choosencolor);
            } else {
                JOptionPane.showMessageDialog(null, "You didn't choose a color!", "No color choosen", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    /**
     * Toggles full-screen mode for the given {@link JFrame}.
     *
     * @param frame takes {@link JFrame} to make the size as fullscreen.
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
     * Displays an exit prompt using {@link JOptionPane#showConfirmDialog(Component, Object)} and terminates the program
     * if confirmed.
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
     * <p>Then check if the user cancelled or not by using {@link JFileChooser#showDialog(Component, String)} which returns
     * {@value JFileChooser#APPROVE_OPTION} if the user chose a file and didn't cancel.</p>
     * <p>
     * Then checks if the user chose a WAV file or not by using {@link JFileChooser#getSelectedFile()} and storing it in
     * a new {@link File} object called {@code selectedSong} and then calls {@link File#getAbsolutePath()} method to get
     * the absolute path of the file then uses {@link String#substring(int, int)} to get the extension. </p>
     *
     * @throws UnsupportedAudioFileException If the user chooses unsupported audio file extension.
     * @throws LineUnavailableException      If the user opens a file that doesn't exist or is being used by another app.
     * @throws IOException                   If the user has an any kind of issues opening the file.
     */
    private void openNewMusic() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Wav files", "wav"));
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
        // Call the makeMove method in AIPlayer to get the move from the AI.
        int[] aiMove = Controller.player.makeMove();
        Controller.xoBoard.DisableAllButtons();
        Controller.xoBoard.getButtons()[aiMove[0]][aiMove[1]].setText("X");
        Controller.xoBoard.getButtons()[aiMove[0]][aiMove[1]].setEnabled(false);
        Controller.xoBoard.EnableAllButtons();
        Controller.xoBoard.setNOfMoves(Controller.xoBoard.getNOfMoves() + 1);
    }

    /**
     * Gets the static player object from the {@link Controller}.
     * @return {@code player} of type {@link AIPlayer}.
     */
    public static AIPlayer getPlayer() {
        return Controller.player;
    }

    /**
     * Sets the {@link Controller#player} object.
     */
    public static void setPlayer(AIPlayer player) {
        Controller.player = player;
    }
}
