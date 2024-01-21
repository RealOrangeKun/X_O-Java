import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * MusicPlayer class which is responsible for playing/pausing/stopping the music.
 *
 * @author Youssef Tarek
 * @version 1.0
 */

public class MusicPlayer {
    /**
     * <p>A {@link CountDownLatch} so the music wouldn't stop until the {@link Clip} is obver.</p>
     *
     * @see CountDownLatch
     * @see Clip
     */
    private static CountDownLatch latch = new CountDownLatch(1);
    /**
     * The {@link Clip} responsible for taking the {@link AudioInputStream} and playing it.
     *
     * @see AudioInputStream
     * @see Clip
     */
    private static Clip clip;
    /**
     * Boolean value to keep track of the music if it's playing or not.
     */
    private static boolean isPlaying;
    /**
     * Boolean value to keep track of the music if it's paused or not.
     */
    private static boolean isPaused;

    /**
     * A long variable to save the pause position of the music when paused to resume where it left off.
     */
    private static long pausePosition = 0;

    /**
     * <p>Takes the {@code music} param and creates a new variable of type {@link File} called {@code musicFile} and stores
     * it in it.</p>
     * <p>Then creates a new variable called {@code audioInputStream} of type {@link AudioInputStream} to get the audio
     * input from {@code musicFile} by using the method {@link AudioSystem#getAudioInputStream(URL)} from the class
     * {@link AudioSystem}.</p>
     * <p>Then uses the {@link AudioSystem#getClip()} method to get the clip and uses {@link Clip#open()} method to open
     * {@code audioInputStream} variable.</p>
     * <p>Then initializes the {@link MusicPlayer#latch} variable.</p>
     * <p>Then adds a new {@link LineListener} using the {@link Clip#addLineListener(LineListener)} method to handle when
     * the music stops and then it sets the {@code isPlaying} variable to false.</p>
     * <p>Then starts playing the music using {@link Clip#start()} method and sets the {@code isPlaying} variable true.</p>
     *
     * @param music The path to the audio file to be played.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     * @throws IOException                   If an I/O error occurs while reading the audio file.
     * @throws LineUnavailableException      If a line matching the specified criteria cannot be opened.
     * @see Clip
     * @see AudioInputStream
     * @see AudioSystem
     * @see File
     * @see CountDownLatch
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
                    if (event.getType() == LineEvent.Type.STOP && !isPaused) {
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

    /**
     * <p>Pauses the music by first checking if the {@link MusicPlayer#clip} is not equal to {@code null} and if the music
     * is still playing by using the {@link Clip#isRunning()} method.</p>
     * <p>If the condition is true is stores the current position of the song inside of the
     * {@link MusicPlayer#pausePosition} variable using the {@link Clip#getMicrosecondPosition()} and then stops the
     * playback by using the {@link Clip#stop()} method and sets {@link MusicPlayer#isPlaying} to false.</p>
     *
     * @see Clip
     */
    public static void pausePlayback() {
        if (clip != null && clip.isRunning()) {
            // Store the current playback position before stopping.
            pausePosition = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
            isPaused = true;
        }
    }

    /**
     * <p>Stops the playback of the music by first checking that the {@link MusicPlayer#clip} is not equal to {@code
     * null} and uses the {@link Clip#stop()} method to stop the music and closes the clip completely by using {@link
     * Clip#close()}</p>
     *
     * @see Clip
     */
    public static void stopPlayback() {
        if (clip != null) {
            clip.stop();
            clip.close();
            isPlaying = false;
        }
    }

    /**
     * <p>Resumes the playback of the music from where it left off by first checking that the {@link MusicPlayer#clip}
     * is not equal to null and then sets the position using the {@link Clip#setMicrosecondPosition(long)} method and
     * uses the value stored in {@link MusicPlayer
     * #pausePosition} and then starts the playback by using {@link Clip#start()} and then sets {@link MusicPlayer
     * #isPlaying} to true.</p>
     *
     * @see Clip
     */
    public static void resumePlayback() {
        if (clip != null) {
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
            isPlaying = true;
            isPaused = false;
        }
    }

    /**
     * Getter for the {@link MusicPlayer#isPlaying} boolean variable.
     *
     * @return {@link MusicPlayer#isPlaying}
     */
    public static boolean isPlaying() {
        return isPlaying;
    }

}
