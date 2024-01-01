import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Controller {
    private static MainMenu mainMenu;
    private static SettingsMenu settingsMenu;

    private static XOBoard xoBoard;

    Controller(MainMenu mainMenu, SettingsMenu settingsMenu, XOBoard xoBoard) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        MusicPlayer.playmusic(System.getProperty("user.dir") + "\\src\\resources\\music2.wav");
        Controller.mainMenu = mainMenu;
        Controller.settingsMenu = settingsMenu;
        Controller.xoBoard = xoBoard;
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
        mainMenu.getExit().addActionListener(e->{
            ExitPrompt();
        });
        mainMenu.getStart().addActionListener(e->{
            Controller.mainMenu.setVisible(false);
            Controller.xoBoard.setVisible(true);
        });
        settingsMenu.getBack().addActionListener(e -> {
            Controller.settingsMenu.setVisible(false);
            Controller.mainMenu.setVisible(true);
        });
        settingsMenu.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                Controller.settingsMenu.setVisible(false);
                Controller.mainMenu.setVisible(true);
            }
        });
        settingsMenu.getFullscreen().addActionListener(e->{
            toggleFullScreen(Controller.settingsMenu);
            toggleFullScreen(Controller.mainMenu);
            toggleFullScreen(Controller.xoBoard);
            Controller.settingsMenu.getFullscreen().setText((SettingsMenu.IsFullScreen())? "Fullscreen ON": "Fullscreen OFF");
        });
        settingsMenu.getMusic().addActionListener(e->{
            if(MusicPlayer.isPlaying()){
                MusicPlayer.pausePlayback();
                Controller.settingsMenu.getMusic().setText("Music OFF");
            }
            else{
                MusicPlayer.resumePlayback();
                Controller.settingsMenu.getMusic().setText("Music ON");
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
    private void ExitPrompt(){
        Image icon2 = new ImageIcon(System.getProperty("user.dir") + "\\src\\resources\\icon.png").getImage().getScaledInstance(50, 50, 100);
        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(icon2));
        if (response == 0) {
            System.exit(0);
        }
    }
}
