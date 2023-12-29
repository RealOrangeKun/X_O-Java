import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class MusicPlayer extends Thread{
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void playmusic(String music) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        try {
            File musicFile = new File(music);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            latch = new CountDownLatch(1);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    latch.countDown();

                }
            });
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }
    public static void waitForPlayback() throws InterruptedException {
        latch.await();
    }
}
