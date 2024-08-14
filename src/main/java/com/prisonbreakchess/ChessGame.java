package com.prisonbreakchess;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public interface ChessGame {

    void start(Stage stage);
    void drawChessBoard(GraphicsContext gc);
    void initChessPiece();
    void action(double mouseX, double mouseY);
    void draw(GraphicsContext gc);

}
