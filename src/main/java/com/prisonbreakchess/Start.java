package com.prisonbreakchess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ChessUtils.width, ChessUtils.height);
        this.stage = stage;
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }
}