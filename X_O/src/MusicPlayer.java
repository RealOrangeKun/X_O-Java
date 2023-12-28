import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class MusicPlayer extends Thread{
    public static void playmusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        try {
            File musicFile = new File("music.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            CountDownLatch latch = new CountDownLatch(3);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    latch.countDown();

                }
            });
            clip.loop(1);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}
