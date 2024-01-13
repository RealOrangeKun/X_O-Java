import javax.swing.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


/**
     * Main class initializes everything and takes username input.
     *
     * @author Youssef Tarek
     * @version 2.0
     */
    public class Main {
        /**
         * @param args
         * @throws UnsupportedAudioFileException
         * @throws LineUnavailableException
         * @throws IOException
         * @throws InterruptedException
         */
        public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
            String username = JOptionPane.showInputDialog(null, "Please enter your name", "Username", JOptionPane.QUESTION_MESSAGE);
            while (!MainMenu.ValidUsername(username)) {
                username = JOptionPane.showInputDialog(null, "Please enter a valid name!", "Username", JOptionPane.QUESTION_MESSAGE);
            }
            new Controller(new MainMenu(username), new SettingsMenu(), new XOBoard());
            System.out.println("Help me plz");
        }

    }

