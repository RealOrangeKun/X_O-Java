// Author: Orangekun
// Last Modified: 30-12-2023



import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        JFrame frame = new JFrame();
        NameMenu menu1 = new NameMenu(frame);
        if (menu1.Userinput()) {
            String username = NameMenu.getUsername();
            System.out.println("Username: " + username);
        } else {
            System.out.println("User canceled");
            System.exit(0);
        }
        MainMenu mainMenu = new MainMenu();

    }
}
