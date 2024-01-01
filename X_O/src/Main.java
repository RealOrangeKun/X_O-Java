// Author: Orangekun
// Last Modified: 31-12-2023
import javax.swing.*;



import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Main{

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        String username = JOptionPane.showInputDialog(null, "Please enter your name", "Username", JOptionPane.QUESTION_MESSAGE);
        while (!MainMenu.ValidUsername(username)) {
            username = JOptionPane.showInputDialog(null, "Please enter a valid name!", "Username", JOptionPane.QUESTION_MESSAGE);
        }
        Controller controller = new Controller(new MainMenu(username), new SettingsMenu(), new XOBoard());
        System.out.println("Help me plz");
    }

}
