import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Controller {
    private static MainMenu mainMenu;
    private static SettingsMenu settingsMenu;
    private final String defualtSong = System.getProperty("user.dir") + "\\src\\resources\\music2.wav";

    private static AIPlayer player;
    private static boolean isAITurn;

    private static XOBoard xoBoard;

    Controller(MainMenu mainMenu, SettingsMenu settingsMenu, XOBoard xoBoard) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        Controller.mainMenu = mainMenu;
        Controller.settingsMenu = settingsMenu;
        Controller.xoBoard = xoBoard;
        MusicPlayer.playmusic(Controller.mainMenu.getUsername().equals("Orange") ? null : defualtSong);
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
            Controller.mainMenu.setVisible(false);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to play with another player?", "Choose player or AI", JOptionPane.YES_NO_OPTION);
            if (response == 0) {
                Controller.xoBoard.setVisible(true);
            } else {
                Controller.xoBoard.setVisible(true);
                player = new AIPlayer();
                makeAIMove();
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
        settingsMenu.getChangeMusic().addActionListener(e -> openNewMusic());
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
        xoBoard.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ExitPrompt();
            }
        });
    }

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

    private void ExitPrompt() {
        Image icon2 = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(50, 50, 100);
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(icon2));
        if (response == 0) {
            System.exit(0);
        }
    }


    public static MainMenu getMainMenu() {
        return Controller.mainMenu;
    }

    private Object openNewMusic() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Wav files", "wav"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedSong = fileChooser.getSelectedFile();
            if (!selectedSong.getAbsolutePath().substring(selectedSong.getAbsolutePath().lastIndexOf('.') + 1).equals("wav")) {
                JOptionPane.showMessageDialog(null, "You need to choose a WAV file!", "Unsupported file chosen", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            try {
                MusicPlayer.stopPlayback();
                MusicPlayer.playmusic(selectedSong.getAbsolutePath());
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
        return (0);
    }

    public static void makeAIMove() {
        int[] aiMove = Controller.player.makeMove();
        Controller.xoBoard.DisableAllButtons();
        Controller.xoBoard.getButtons()[aiMove[0]][aiMove[1]].setText("X");
        Controller.xoBoard.getButtons()[aiMove[0]][aiMove[1]].setEnabled(false);
        Controller.xoBoard.EnableAllButtons();
        Controller.xoBoard.setNOfMoves(Controller.xoBoard.getNOfMoves() + 1);
    }

    public static AIPlayer getPlayer() {
        return player;
    }

    public static void setPlayer(AIPlayer player) {
        Controller.player = player;
    }
}
