// Author: Orangekun
// Last Modified: 30-12-2023
/* If you want to run the code with the music and logos change the path in the classes (I am sorry)
   or you can just run the JAR file or the EXE file honestly I would do that
*/



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
        new MainMenu();
    }
}
