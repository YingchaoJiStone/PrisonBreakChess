package com.prisonbreakchess;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BasicChess extends Application implements ChessGame {

    // Define the dimensions of the chessboard and the size of each unit
    private int width = ChessUtils.width;
    private int height = ChessUtils.height;
    private int unitSize = ChessUtils.unitSize;
    private int startPositionX = unitSize * 2;
    private int startPositionY = unitSize * 2;
    private int endPositionX = unitSize * 16;
    private int endPositionY = unitSize * 16;
    private int numX = 3;
    private int numY = 3;
    private double pieceRadius = (double) (endPositionX - startPositionX) / (numX + 1) / 3;

    // Factory to create chess pieces and manage their positions
    private ChessPiecesFactory chessPiecesFactory;
    private List<ChessPiece> points; // List of all chess pieces on the board
    private List<ChessPiece> validPoints; // List of valid moves for a selected piece
    private ChessGraph graph; // Graph representing the connections between chess pieces
    private Player player1; // Player 1 (Black)
    private Player player2; // Player 2 (White)
    private ChessPiece selected; // Currently selected chess piece

    // Constructor to initialize the chess game
    public BasicChess() {
        initChessPiece();
    }

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawChessBoard(gc); // Draw the chessboard
        draw(gc); // Draw the chess pieces

        // Handle mouse clicks on the canvas
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double mouseX = mouseEvent.getSceneX();
                double mouseY = mouseEvent.getSceneY();
                action(mouseX, mouseY); // Handle the action based on the mouse click
                gc.clearRect(0, 0, width, height); // Clear the canvas
                drawChessBoard(gc); // Redraw the chessboard
                draw(gc); // Redraw the chess pieces
            }
        });

        Scene scene = new Scene(root, width, height);
        // Handle key presses (e.g., SPACE key for pause menu)
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    handleSpaceKey(); // Show the pause menu
                    break;
                default:
                    break;
            }
        });

        root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();
    }

    // Method to draw the chessboard
    public void drawChessBoard(GraphicsContext gc) {
        gc.setFill(Color.web("EEBC06FF")); // Set the background color
        gc.fillRect(0, 0, width, height); // Fill the canvas with the background color
        gc.setLineWidth(5); // Set the line width for the chessboard
        chessPiecesFactory.drawChessBoard(gc); // Draw the chessboard using the factory
    }

    // Method to initialize the chess pieces
    public void initChessPiece() {
        chessPiecesFactory = new ChessPiecesFactory(startPositionX, startPositionY, endPositionX, endPositionY, numX, numY, pieceRadius);
        points = chessPiecesFactory.getPoints(); // Get all the chess pieces
        graph = chessPiecesFactory.getGraph(); // Get the graph representing connections
        selected = new ChessPiece(); // Initialize the selected piece

        // Print the graph connections for debugging
        for (Integer integer : graph.getGraph().keySet()) {
            System.out.print(integer + "  ");
            for (Integer i : graph.getGraph().get(integer)) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        // Initialize players and assign pieces to them
        player1 = new Player(Color.BLACK);
        List<ChessPiece> blackPieces = player1.getChessPieces();
        player2 = new Player(Color.WHITE);
        List<ChessPiece> whitePieces = player2.getChessPieces();
        for (int i = 0; i < points.size(); i++) {
            if (i < numX) {
                ChessPiece chessPiece = points.get(i);
                chessPiece.setColor(Color.WHITE); // Assign white pieces to player 2
                whitePieces.add(chessPiece);
            }
            if (i >= points.size() - numX) {
                ChessPiece chessPiece = points.get(i);
                chessPiece.setColor(Color.BLACK); // Assign black pieces to player 1
                blackPieces.add(chessPiece);
            }
        }
    }

    // Method to handle mouse clicks and move pieces
    public void action(double mouseX, double mouseY) {
        // If a piece is already selected, reset its radius
        if (selected != null) {
            selected.setRadius(pieceRadius);
        }
        validPoints = new ArrayList<>();

        // Check if the mouse click is on a chess piece
        for (ChessPiece chessPiece : points) {
            if (!chessPiece.getColor().equals(Color.TRANSPARENT)) {
                if (mouseX >= chessPiece.getPositionX() - pieceRadius && mouseX <= chessPiece.getPositionX() + pieceRadius) {
                    if (mouseY >= chessPiece.getPositionY() - pieceRadius && mouseY <= chessPiece.getPositionY() + pieceRadius) {
                        chessPiece.setRadius(pieceRadius + pieceRadius / 4); // Highlight the selected piece
                        selected = chessPiece; // Set the selected piece
                    }
                }
            } else {
                // If the clicked position is empty, check if it's a valid move
                List<Integer> paths = graph.getGraph().get(selected.getId());
                for (Integer id : paths) {
                    if (chessPiece.getId() == id) {
                        if (mouseX >= chessPiece.getPositionX() - pieceRadius && mouseX <= chessPiece.getPositionX() + pieceRadius) {
                            if (mouseY >= chessPiece.getPositionY() - pieceRadius && mouseY <= chessPiece.getPositionY() + pieceRadius) {
                                chessPiece.setColor(selected.getColor()); // Move the selected piece to the new position
                                selected.setColor(Color.TRANSPARENT); // Clear the old position
                            }
                        }
                    }
                }
            }
        }


    }

    // Method to draw all the chess pieces on the canvas
    public void draw(GraphicsContext gc) {
        for (ChessPiece chessPiece : points) {
            gc.setFill(chessPiece.getColor());
            gc.fillOval(chessPiece.getPositionX() - chessPiece.getRadius(), chessPiece.getPositionY() - chessPiece.getRadius(),
                    chessPiece.getRadius() * 2, chessPiece.getRadius() * 2);
        }
    }

    // Method to handle the SPACE key press (pause menu)
    private void handleSpaceKey() {
        Button continueGame = new Button("Continue Game");
        Button backToMenu = new Button("Back To Menu");

        // Set the position and style of the "Continue Game" button
        continueGame.setLayoutX(width / 2 - 100);
        continueGame.setLayoutY(height / 2 - 100);
        continueGame.setPrefSize(200, 50);
        continueGame.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        continueGame.setOnAction(event -> {
            continueGame.setVisible(false);
            backToMenu.setVisible(false);
        });

        // Set the position and style of the "Back To Menu" button
        backToMenu.setLayoutX(width / 2 - 100);
        backToMenu.setLayoutY(height / 2 + 50);
        backToMenu.setPrefSize(200, 50);
        backToMenu.setStyle("-fx-font-size: 16px; -fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        backToMenu.setOnAction(event -> {
            continueGame.setVisible(false);
            backToMenu.setVisible(false);
            try {
                new Start().start(Start.stage); // Return to the main menu
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Add the buttons to the root pane
        Pane root = (Pane) Start.stage.getScene().getRoot();
        root.getChildren().addAll(continueGame, backToMenu);
    }
}