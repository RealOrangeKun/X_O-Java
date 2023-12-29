import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        String[] songs={"music.wav","music2.wav"};
        JFrame frame = new JFrame();
        NameMenu menu1 = new NameMenu(frame);
        if (menu1.Userinput()) {
            String username = menu1.getUsername();
            System.out.println("Username: " + username);
        } else {
            System.out.println("User canceled");
            System.exit(0);
        }
        JFrame frame1 = new JFrame();
        frame1.setVisible(true);
        for(String s: songs){
            MusicPlayer.playmusic(s);
            MusicPlayer.waitForPlayback();
        }

    }
}
