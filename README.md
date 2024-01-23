# XO Game

A simple Java implementation of the classic XO (Tic Tac Toe) game using [Javax](https://docs.oracle.com/javase/8/docs/api/javax/net/package-summary.html).[swing](https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/package-summary.html) and this project follows the [MVC](https://dotnet.microsoft.com/en-us/apps/aspnet/mvc#:~:text=MVC%20is%20a%20design%20pattern,to%20achieve%20separation%20of%20concerns.) design pattern.

## Table of Contents
- [Project Title](#xo-game)
- [Description](#description)
- [Features](#features)
- [Code Documentation](#code-documentation)


## Description

At first the program will ask for your name and it wont work if you submit an empty name and you can submit by hitting enter or pressing on the submit button.

Song in the background: Summertime - Cinnamons (TRAP REMIX) Kimi No Toriko Song (Prod. Levnx)
Link: https://www.youtube.com/watch?v=oKuhRPtwm9M&ab_channel=XReachNation

## Features

- Settings menu to enable and turn on/off music and fullscreen.
- Player vs Player mode.
- Player vs AI.
- Change background color.
- Change song.

## Code Documentation
Library used in this project: [Swing](https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/package-summary.html) Java GUI Library.

The project consists of 3 main classes [Controller](#controllerjava), [Settings Menu](#settingsmenujava), [Main Menu](#mainmenujava), [XO Board](#xoboardjava), [Music Player](#musicplayerjava), [AI Player](#aiplayerjava).

- ### [Controller.java](X_O\src\Controller.java)
    The `Controller` class in this project serves as a central component responsible for handling user input, managing interactions between the user interface and the underlying business logic, and controlling the flow of the application.
    #### Overview
    The primary responsibilities of the `Controller` class include:
    - Receiving user input from the UI components.
    - Validating and processing user input.
    - Orchestrating interactions with other components, such as the [Settings Menu]() and [XO Board]().
    - Managing the overall flow and navigation within the application. 
    #### Usage Example
    ```java
    String username = " ";
    // This is the code used in the Main.java class
    new Controller(new MainMenu(username), new SettingsMenu(), new XOBoard());
    ```
- ### [SettingsMenu.java](X_O\src\SettingsMenu.java)
    The `SettingsMenu` class in this project is responsible for managing and presenting settings options to the user. It provides a user interface for configuring various aspects of the application like changing the music, the color of the game and turning on/ff the music and turning fullscreen on/ff.
    #### Overview
    The primary responsibilities of the `SettingsMenu` class include:
    - Displaying a menu of configurable settings.
    - Capturing user input for adjusting settings.
    - Persisting the changes made to settings.
    #### Key Methods
    ##### `public JButton getChangeColor()`

    Getter for the change color button so the controller can handle what happens when the user clicks on it to change the color to all the other menus.
    ```java
    public JButton getChangeColor() {
        return changeColor;
    }
    ```
    ##### `public JButton getChangeMusic()`

    Getter for the change music button so the controller can handle the music file input and play the new music using the [`MusicPlayer`](#musicplayer.java) class.
    ```java
    public JButton getChangeMusic() {
        return changeMusic;
    }
    ```
- ### [MainMenu.java](X_O\src\MainMenu.java)
    The `MainMenu` class in this project serves as the main entry point and navigation hub for the application. It is responsible for presenting the user with the primary menu options and having access to the settings menu and starting the game itself.
    #### Overview
    The primary responsibilities of the `MainMenu` class include:
    - Displaying the main menu options to the user.
    - Handling user input for navigating between different sections of the application.
    - Initiating the corresponding actions or transitions based on user selections.
    #### Key Methods
    ##### `public static boolean ValidUsername(String s)`
    The method which checks the username if its valid or not. The validity of the username is that it has to not be null and the length must be less than 15, it can't have any non letters and it can't be empty.
    ```java
    public static boolean ValidUsername(String s) {
        if (s == null) System.exit(0);
        return (s.length() < 15 && !Pattern.compile("[^a-zA-Z]").matcher(s).find() && !s.isEmpty());
    }
    ```
- ### [XOBoard.java](X_O\src\XOBoard.java)
    The `XOBoard` class in this project represents the game board for a Tic-Tac-Toe (XO) game. It manages the state of the board, tracks player moves, and determines the game's outcome.
    #### Overview
    The primary responsibilities of the `XOBoard` class include:
    - Maintaining the state of the game board (e.g., empty, X, O).
    - Validating and applying player moves.
    - Checking for a winning condition or a draw.
    - Resetting the board for a new game.
    #### Key Methods
    ##### `public boolean IsWinner()`
    This method checks if there is a winner in the game by looping through the 2d array of `JButtons` and checks the text if it finds 3 consecutive Xs or Os then it returns true.
    ```java
    public boolean IsWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][1].getText().equals(buttons[i][2].getText())) {
                if (buttons[i][0].getText().equals("X")) {
                    showGameEndPrompt("X Wins!");
                    return true;
                } else if (buttons[i][0].getText().equals("O")) {
                    showGameEndPrompt("O Wins!");
                    return true;
                }
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[1][i].getText().equals(buttons[2][i].getText())) {
                if (buttons[0][i].getText().equals("X")) {
                    showGameEndPrompt("X Wins!");
                    return true;
                } else if (buttons[0][i].getText().equals("O")) {
                    showGameEndPrompt("O Wins!");
                    return true;
                }
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText())) {
            if (buttons[0][0].getText().equals("X")) {
                showGameEndPrompt("X Wins!");
                return true;
            } else if (buttons[0][0].getText().equals("O")) {
                showGameEndPrompt("O Wins!");
                return true;
            }
        }

        if (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText())) {
            if (buttons[0][2].getText().equals("X")) {
                showGameEndPrompt("X Wins!");
                return true;
            } else if (buttons[0][2].getText().equals("O")) {
                showGameEndPrompt("O Wins!");
                return true;
            }
        }

        return false;
    }
    ```
- ### [MusicPlayer.java](X_O\src\MusicPlayer.java)
    The `MusicPlayer` class in this project is responsible for handling the playback of music tracks. It provides functionality for controlling the playback and interacting with the audio resources.
    #### Overview
    The primary responsibilities of the `MusicPlayer` class include:
    - Loading and playing audio tracks.
    - Pausing, resuming, and stopping playback.
    #### Key Methods
    ##### `public static void playmusic(String music)`
    This is the method which is responsible for playing the music and it takes the paramtere `String music` which is the path to the song file(.wav).
    ```java
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
    ```
    ##### `public static void pausePlayback()`
    This is the method responsible for pausing the music and storing the position where the song stopped on so the song can me resumed later.
    ```java
    public static void pausePlayback() {
        if (clip != null && clip.isRunning()) {
            // Store the current playback position before stopping.
            pausePosition = clip.getMicrosecondPosition();
            clip.stop();
            isPlaying = false;
            isPaused = true;
        }
    }
    ```
    ##### `public static void resumePlayback()`
    This is the method responsible for resuming the music by using the value left off by the `pausePlayback()` function.
    ```java
    public static void resumePlayback() {
        if (clip != null) {
            clip.setMicrosecondPosition(pausePosition);
            clip.start();
            isPlaying = true;
            isPaused = false;
        }
    }
    ```

## License
This project is licensed under the [Apache License](LICENSE)
