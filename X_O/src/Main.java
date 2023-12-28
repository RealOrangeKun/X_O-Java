import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        MusicPlayer mp = new MusicPlayer();
        JFrame frame = new JFrame();
        NameMenu menu1 = new NameMenu(frame);
        MusicPlayer.playmusic();
        if (menu1.Userinput()) {
            String username = menu1.getUsername();
            System.out.println("Username: " + username);
        } else {
            System.out.println("User canceled");
            System.exit(0);
        }

    }
}
