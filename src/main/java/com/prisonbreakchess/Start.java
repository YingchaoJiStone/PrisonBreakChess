package com.prisonbreakchess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Start class is the entry point for the JavaFX application.
 * It initializes the primary stage and loads the start screen from an FXML file.
 */
public class Start extends Application {
    // Static reference to the primary stage for global access
    static Stage stage;

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }

    /**
     * Initializes the primary stage and sets up the start screen.
     *
     * @param stage The primary stage (window) for the JavaFX application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file for the start screen
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("start-view.fxml"));
        // Create a scene with the specified dimensions from ChessUtils
        Scene scene = new Scene(fxmlLoader.load(), ChessUtils.width, ChessUtils.height);
        this.stage = stage; // Store the stage reference for global access
        stage.setTitle("Chess"); // Set the title of the window
        stage.setScene(scene); // Set the scene to the stage
        stage.show(); // Display the stage
    }
}