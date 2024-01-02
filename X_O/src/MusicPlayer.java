import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;


public class MusicPlayer extends Thread {
    private static CountDownLatch latch = new CountDownLatch(1);
    private static Clip clip;

    private static boolean isPlaying;
    private static long pausePosition = 0;

    public static void playmusic(String music) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        try {
            File musicFile = new File(music);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            latch = new CountDownLatch(1);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    isPlaying = false;
                    latch.countDown();
                }
            });
            clip.start();
            isPlaying = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public static void pausePlayback() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
        }
    }

    public static void stopPlayback() {
        if (clip != null) {
            clip.stop();
            clip.close();
            isPlaying = false;
        }
    }

    public static void resumePlayback() {
        if (clip != null) {
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
            isPlaying = true;
        }
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

}
