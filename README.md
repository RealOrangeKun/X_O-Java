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

The project consists of 3 main classes [Controller](#controllerjava), [Settings Menu](#settingsmenujava), [Main Menu](X_O\src\MainMenu.java), [XO Board](X_O\src\XOBoard.java), [Music Player](X_O\src\MusicPlayer.java), [AI Player](X_O\src\AIPlayer.java).

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


## License
This project is licensed under the [Apache License](LICENSE)
