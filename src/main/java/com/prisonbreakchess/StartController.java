package com.prisonbreakchess;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * The StartController class handles user interactions on the start screen.
 * It manages button clicks and updates the UI accordingly.
 */
public class StartController {
    // FXML annotation to inject the welcomeText Label from the FXML file
    @FXML
    private Label welcomeText;

    /**
     * Handles the "Human vs Machine" button click event.
     * Updates the welcome text and starts a new Human vs Machine chess game.
     *
     * @param actionEvent The event triggered by the button click.
     * @throws Exception If there is an error starting the game.
     */
    @FXML
    public void onHumanMachineButtonClick(ActionEvent actionEvent) throws Exception {
        welcomeText.setText("Welcome to Human-Machine!"); // Update the welcome text
        new BasicChess().start(Start.stage); // Start a new Human vs Machine game
    }

    /**
     * Handles the "Two Players" button click event.
     * Updates the welcome text and starts a new two-player chess game.
     *
     * @param actionEvent The event triggered by the button click.
     */
    public void twoPlayersButtonClick(ActionEvent actionEvent) {
        welcomeText.setText("Welcome to two players!"); // Update the welcome text
        new BasicChess().start(Start.stage); // Start a new two-player game
    }

    /**
     * Handles the "Settings" button click event.
     * Updates the welcome text to indicate the settings screen.
     *
     * @param actionEvent The event triggered by the button click.
     */
    public void settingButtonClick(ActionEvent actionEvent) {
        welcomeText.setText("Welcome to setting!"); // Update the welcome text
    }

    /**
     * Handles the "Exit" button click event.
     * Exits the application.
     *
     * @param actionEvent The event triggered by the button click.
     */
    public void exitButtonClick(ActionEvent actionEvent) {
        Platform.exit(); // Exit the application
    }
}