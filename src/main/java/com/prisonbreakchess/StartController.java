package com.prisonbreakchess;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StartController {
    @FXML
    private Label welcomeText;

    @FXML
    public void onHumanMachineButtonClick(ActionEvent actionEvent) throws Exception {
        welcomeText.setText("Welcome to Human-Machine!");
        new BasicChess().start(Start.stage);
    }

    public void twoPlayersButtonClick(ActionEvent actionEvent) {
        welcomeText.setText("Welcome to two players!");
        new BasicChess().start(Start.stage);
    }

    public void settingButtonClick(ActionEvent actionEvent) {
        welcomeText.setText("Welcome to setting!");
    }

    public void exitButtonClick(ActionEvent actionEvent) {
        welcomeText.setText("Welcome to exit!");
    }
}