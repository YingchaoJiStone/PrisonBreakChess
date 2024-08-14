package com.prisonbreakchess;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BasicChess extends Application implements ChessGame {

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
    private ChessPiecesFactory chessPiecesFactory;
    private List<ChessPiece> points;
    private List<ChessPiece> validPoints;
    private ChessGraph graph;
    private Player player1;
    private Player player2;
    private ChessPiece selected;

    public BasicChess() {
        initChessPiece();
    }

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawChessBoard(gc);
        draw(gc);

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double mouseX = mouseEvent.getSceneX();
                double mouseY = mouseEvent.getSceneY();
                action(mouseX, mouseY);
                gc.clearRect(0, 0, width, height);
                drawChessBoard(gc);
                draw(gc);

            }
        });


        root.getChildren().add(canvas);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }


    public void drawChessBoard(GraphicsContext gc) {
        gc.setFill(Color.web("EEBC06FF"));
        gc.fillRect(0, 0, width, height);
        gc.setLineWidth(5);
        chessPiecesFactory.drawChessBoard(gc);
    }

    public void initChessPiece() {
        chessPiecesFactory = new ChessPiecesFactory(startPositionX, startPositionY, endPositionX, endPositionY, numX, numY, pieceRadius);
        points = chessPiecesFactory.getPoints();
        graph = chessPiecesFactory.getGraph();
        selected = new ChessPiece();
        for (Integer integer : graph.getGraph().keySet()) {
            System.out.print(integer + "  ");
            for (Integer i : graph.getGraph().get(integer)) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        player1 = new Player(Color.BLACK);
        List<ChessPiece> blackPieces = player1.getChessPieces();
        player2 = new Player(Color.WHITE);
        List<ChessPiece> whitePieces = player2.getChessPieces();
        for (int i = 0; i < points.size(); i++) {
            if (i < numX) {
                ChessPiece chessPiece = points.get(i);
                chessPiece.setColor(Color.WHITE);
                whitePieces.add(chessPiece);
            }
            if (i >= points.size() - numX) {
                ChessPiece chessPiece = points.get(i);
                chessPiece.setColor(Color.BLACK);
                blackPieces.add(chessPiece);
            }
        }
    }



    public void action(double mouseX, double mouseY) {
        //take up a chess piece
        if (selected != null) {
            selected.setRadius(pieceRadius);
        }
        validPoints = new ArrayList<>();
        for (ChessPiece chessPiece : points) {
            if (!chessPiece.getColor().equals(Color.TRANSPARENT)) {
                if (mouseX >= chessPiece.getPositionX() - pieceRadius && mouseX <= chessPiece.getPositionX() + pieceRadius) {
                    if (mouseY >= chessPiece.getPositionY() - pieceRadius && mouseY <= chessPiece.getPositionY() + pieceRadius) {
                        chessPiece.setRadius(pieceRadius + pieceRadius / 4);
                        selected = chessPiece;
                    }
                }
            }else {
                List<Integer> paths = graph.getGraph().get(selected.getId());
                for (Integer id : paths) {
                    if (chessPiece.getId() == id){
                        if (mouseX >= chessPiece.getPositionX() - pieceRadius && mouseX <= chessPiece.getPositionX() + pieceRadius) {
                            if (mouseY >= chessPiece.getPositionY() - pieceRadius && mouseY <= chessPiece.getPositionY() + pieceRadius) {
                                chessPiece.setColor(selected.getColor());
                                selected.setColor(Color.TRANSPARENT);
                            }
                        }
                    }
                }

            }
        }

    }

    public void draw(GraphicsContext gc) {

        for (ChessPiece chessPiece : points) {
            gc.setFill(chessPiece.getColor());
            gc.fillOval(chessPiece.getPositionX() - chessPiece.getRadius(), chessPiece.getPositionY() - chessPiece.getRadius(),
                    chessPiece.getRadius() * 2, chessPiece.getRadius() * 2);
        }
    }


}