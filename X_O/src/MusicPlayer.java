import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/*
 * Class representing a simple music player.
 */
public class MusicPlayer extends Thread {
    private static CountDownLatch latch = new CountDownLatch(1);
    private static Clip clip;

    private static boolean isPlaying;
    private static long pausePosition = 0;

    /*
     * Plays the specified music file.
     *
     * @param music The path to the audio file to be played.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     * @throws IOException If an I/O error occurs while reading the audio file.
     * @throws LineUnavailableException If a line matching the specified criteria cannot be opened.
     */
    public static void playmusic(String music) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (music != null) {
            try {
                File musicFile = new File(music);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                latch = new CountDownLatch(1);

                // Add a LineListener to detect when the audio playback stops.
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        isPlaying = false;
                        latch.countDown();
                    }
                });

                // Start playing the audio.
                clip.start();
                isPlaying = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Pauses the audio playback.
     */
    public static void pausePlayback() {
        if (clip != null && clip.isRunning()) {
            // Store the current playback position before stopping.
            pausePosition = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
        }
    }

    /*
     * Stops the audio playback.
     */
    public static void stopPlayback() {
        if (clip != null) {
            clip.stop();
            clip.close();
            isPlaying = false;
        }
    }

    /*
     * Resumes audio playback from the last paused position.
     */
    public static void resumePlayback() {
        if (clip != null) {
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
            isPlaying = true;
        }
    }

    /*
     * Returns true if audio is currently playing; false otherwise.
     */
    public static boolean isPlaying() {
        return isPlaying;
    }

}
