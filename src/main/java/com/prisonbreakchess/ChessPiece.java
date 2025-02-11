package com.prisonbreakchess;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The ChessPiece class represents a chess piece on the board.
 * It stores information about the piece's position, size, color, and unique identifier.
 * This class also provides a method to create a visual representation of the piece using JavaFX's Circle class.
 */
public class ChessPiece {
    private int id; // Unique identifier for the chess piece
    private int positionX; // X-coordinate of the piece's position on the board
    private int positionY; // Y-coordinate of the piece's position on the board
    private double radius; // Radius of the piece (used for rendering)
    private Color color; // Color of the piece (e.g., BLACK or WHITE)
    private Circle circle; // JavaFX Circle object for rendering the piece visually

    /**
     * Default constructor for ChessPiece.
     * Initializes an empty chess piece with no properties set.
     */
    public ChessPiece() {
    }

    /**
     * Parameterized constructor for ChessPiece.
     *
     * @param id        The unique identifier for the chess piece.
     * @param positionX The X-coordinate of the piece's position on the board.
     * @param positionY The Y-coordinate of the piece's position on the board.
     * @param radius    The radius of the piece (used for rendering).
     * @param color     The color of the piece.
     */
    public ChessPiece(int id, int positionX, int positionY, double radius, Color color) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        this.color = color;
    }

    // Getter and Setter methods for the ChessPiece properties

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Creates and returns a JavaFX Circle object representing the chess piece.
     * The circle is positioned at (positionX, positionY) with the specified radius and color.
     *
     * @return A Circle object representing the chess piece.
     */
    public Circle getCircle() {
        circle = new Circle(positionX, positionY, radius, color);
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}