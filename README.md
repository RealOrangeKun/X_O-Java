## Table of Contents
- [Project Title](#xo-game)
- [Description](#description)
- [Features](#features)
- [Code Documentation](#code-documentation)
- [How to run](#how-to-run)


# XO Game

A simple Java implementation of the classic XO (Tic Tac Toe) game using [Javax](https://docs.oracle.com/javase/8/docs/api/javax/net/package-summary.html).[swing](https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/package-summary.html) and this project follows the [MVC](https://dotnet.microsoft.com/en-us/apps/aspnet/mvc#:~:text=MVC%20is%20a%20design%20pattern,to%20achieve%20separation%20of%20concerns.) design pattern.




## Description

The well known and beloved Tic Tac Toe game made with java! The game has background music playing in the background while the player enjoys the game and it gives the player the choice to change the song or even change the background color of the game. The player can choose to play with another human locally or an AI that plays using the MiniMax Algorithm to make it's moves.

Song in the background: [GigaChad 8bit Song](https://www.youtube.com/watch?v=VBs7Nek8YDw&ab_channel=NathCraft)

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
- ### [AIPlayer.java](X_O\src\AIPlayer.java)
    The `AIPlayer` class in this project represents an artificial intelligence player for a game. It is responsible for making intelligent decisions, typically in the context of a game or simulation, by implementing the minimax algorithm.
    #### Overview
    The primary responsibilities of the `AIPlayer` class include:
    - Evaluating the current game state using the minimax algorithm to make informed decisions.
    - Providing methods for adjusting the difficulty level and learning from past experiences.
    #### Key Methods
    ##### `public int[] makeMove()`
    This is the method reponsible for maing the move in the 2d board in the `AIPlayer` by using the MiniMax algorithm to find the best move on the board and then it returns an array with `index 0` as the x-coordinate of the move and `index 1` as the y-coordinate of the move.
    ```java
    public int[] makeMove() {
        // Attempt to win the game
        if (isValidMove(1, 1)) {
            updateBoard(1, 1, 'X');
            return new int[]{1, 1};
        } else {
            // Block opponent's winning moves
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (isValidMove(i, j)) {
                        updateBoard(i, j, 'X');
                        if (isWinner(consoleBoard, 'X')) {
                            return new int[]{i, j};
                        }
                        updateBoard(i, j, ' ');
                    }
                }
            }

            // Play optimally using minimax algorithm
            int[] bestMove = minimax(consoleBoard);
            while (!isValidMove(bestMove[0], bestMove[1]))
                bestMove = minimax(consoleBoard);
            consoleBoard[bestMove[0]][bestMove[1]] = 'X';
            return bestMove;
        }
    }
    ```
    #### Usage Example
    ```java
    AIPlayer p = new AIPlayer();
    p.makeMove();
    // Human makes a move in cell 1,2 for example
    p.updateBoard(1,2,'O');
    ```
## How to run
### Using Releases
To run the game itself you would need to go to latest releast and just download the source code it should have the game as a `.jar` or `.exe` you can run using both.
### Cloning the repo
You can run the game by cloning the repo itself and running it in the console by using the following commands in `git-bash`
```bash
# Clone the repo
git clone https://github.com/RealOrangeKun/X_O-Java.git 
# Go to the repo folder
cd /path/to/repo/X_O/
# Run the .exe file (you can also run the .jar file)
./X_O.exe
```

## License
This project is licensed under the [Apache License](LICENSE)
