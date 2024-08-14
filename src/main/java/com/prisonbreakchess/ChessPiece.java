package com.prisonbreakchess;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ChessPiece {
    private int id;

    private int positionX;
    private int positionY;
    private double radius;
    private Color color;
    private Circle circle;


    public ChessPiece() {
    }
    public ChessPiece(int id, int positionX, int positionY, double radius, Color color) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        this.color = color;
    }

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

    public Circle getCircle() {
        circle = new Circle(positionX,positionY,radius,color);
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

}
