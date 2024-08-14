package com.prisonbreakchess;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private Color color;
    private List<ChessPiece> chessPieces;
    private int wins;
    private int failures;
    private int score;

    public Player(Color color){
        this.color = color;
        chessPieces = new ArrayList<ChessPiece>();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }

    public void setChessPieces(List<ChessPiece> chessPieces) {
        this.chessPieces = chessPieces;
    }
}
