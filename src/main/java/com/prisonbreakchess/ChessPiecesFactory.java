package com.prisonbreakchess;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ChessPiecesFactory {
    // Coordinates defining the boundaries of the chessboard
    private int startPositionX;
    private int startPositionY;
    private int endPositionX;
    private int endPositionY;

    // Intervals between chess pieces on the X and Y axes
    private int intervalX;
    private int intervalY;

    // Number of pieces along the X and Y axes
    private int numsX;
    private int numsY;

    // Radius of each chess piece
    private double pieceRadius;

    // 2D list to store chess pieces in a matrix format
    private List<List<ChessPiece>> matrixPoints;

    // Flat list to store all chess pieces
    private List<ChessPiece> points;

    // Graph representing valid moves between chess pieces
    private ChessGraph graph;

    // Constructor to initialize the ChessPiecesFactory with board dimensions and piece properties
    public ChessPiecesFactory(int startPositionX, int startPositionY, int endPositionX, int endPositionY, int numsX, int numsY, double pieceRadius) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.endPositionX = endPositionX;
        this.endPositionY = endPositionY;
        this.intervalX = (endPositionX - startPositionX) / (numsX - 1); // Calculate X-axis interval
        this.intervalY = (endPositionY - startPositionY) / (numsY - 1); // Calculate Y-axis interval
        this.numsX = numsX;
        this.numsY = numsY;
        this.pieceRadius = pieceRadius;
    }

    // Generates and returns a 2D list of chess pieces arranged in a matrix format
    public List<List<ChessPiece>> getMatrixPoints() {
        matrixPoints = new ArrayList<>();
        int tempY = startPositionY;
        int id = 0; // Unique identifier for each chess piece
        for (int i = 0; i < numsY; i++) {
            List<ChessPiece> linePoints = new ArrayList<>();
            int tempX = startPositionX;
            for (int j = 0; j < numsX; j++) {
                // Create a new chess piece with a unique ID, position, and transparent color
                ChessPiece chessPiece = new ChessPiece(id, tempX, tempY, pieceRadius, Color.TRANSPARENT);
                linePoints.add(chessPiece);
                id++;
                tempX += intervalX; // Move to the next X position
            }
            matrixPoints.add(linePoints);
            tempY += intervalY; // Move to the next Y position
        }
        return matrixPoints;
    }

    // Draws the chessboard on the provided GraphicsContext
    public void drawChessBoard(GraphicsContext gc) {
        int width = endPositionX - startPositionX;
        int height = endPositionY - startPositionY;
        int widthGap = width / (numsX - 1); // Calculate horizontal gap between lines
        int heightGap = height / (numsY - 1); // Calculate vertical gap between lines

        // Draw vertical lines
        int temp = startPositionX;
        for (int i = 0; i < numsX; i++) {
            gc.strokeLine(temp, startPositionY, temp, endPositionY);
            temp += widthGap;
        }

        // Draw horizontal lines
        temp = startPositionY;
        for (int i = 0; i < numsY; i++) {
            gc.strokeLine(startPositionX, temp, endPositionX, temp);
            temp += heightGap;
        }

        // Draw diagonal lines
        gc.strokeLine(startPositionX, startPositionY, endPositionX, endPositionY);
        gc.strokeLine(endPositionX, startPositionY, startPositionX, endPositionY);
    }

    // Generates and returns a ChessGraph representing valid moves between chess pieces
    public ChessGraph getGraph() {
        List<Integer> vertices = new ArrayList<>(); // List of all piece IDs
        List<List<Integer>> edges = new ArrayList<>(); // List of edges (connections between pieces)

        // Iterate through the matrix of chess pieces to create vertices and edges
        for (int i = 0; i < matrixPoints.size(); i++) {
            List<ChessPiece> list = matrixPoints.get(i);
            for (int j = 0; j < list.size(); j++) {
                vertices.add(list.get(j).getId()); // Add the piece's ID to the vertices list

                // Add horizontal edges (left and right connections)
                if (j == 0) {
                    List<Integer> edge1 = new ArrayList<>();
                    edge1.add(list.get(j).getId());
                    edge1.add(list.get(j + 1).getId());
                    edges.add(edge1);
                    addVerticalEdges(edges, i, j); // Add vertical edges
                } else if (j < list.size() - 1) {
                    List<Integer> edge1 = new ArrayList<>();
                    edge1.add(list.get(j).getId());
                    edge1.add(list.get(j - 1).getId());
                    edges.add(edge1);
                    List<Integer> edge2 = new ArrayList<>();
                    edge2.add(list.get(j).getId());
                    edge2.add(list.get(j + 1).getId());
                    edges.add(edge2);
                    addVerticalEdges(edges, i, j); // Add vertical edges
                } else if (j == list.size() - 1) {
                    List<Integer> edge = new ArrayList<>();
                    edge.add(list.get(j).getId());
                    edge.add(list.get(j - 1).getId());
                    edges.add(edge);
                    addVerticalEdges(edges, i, j); // Add vertical edges
                }
            }
        }

        addDiagonalEdges(edges); // Add diagonal edges
        graph = new ChessGraph(vertices, edges); // Create the graph
        System.out.println(graph.toString()); // Print the graph for debugging
        return graph;
    }

    // Adds vertical edges (up and down connections) between chess pieces
    public void addVerticalEdges(List<List<Integer>> edges, int i, int j) {
        if (i == 0) {
            List<Integer> edge2 = new ArrayList<>();
            edge2.add(matrixPoints.get(i).get(j).getId());
            edge2.add(matrixPoints.get(i + 1).get(j).getId());
            edges.add(edge2);
        } else if (i < matrixPoints.size() - 1) {
            List<Integer> edge1 = new ArrayList<>();
            edge1.add(matrixPoints.get(i).get(j).getId());
            edge1.add(matrixPoints.get(i - 1).get(j).getId());
            edges.add(edge1);
            List<Integer> edge2 = new ArrayList<>();
            edge2.add(matrixPoints.get(i).get(j).getId());
            edge2.add(matrixPoints.get(i + 1).get(j).getId());
            edges.add(edge2);
        } else if (i == matrixPoints.size() - 1) {
            List<Integer> edge = new ArrayList<>();
            edge.add(matrixPoints.get(i).get(j).getId());
            edge.add(matrixPoints.get(i - 1).get(j).getId());
            edges.add(edge);
        }
    }

    // Adds diagonal edges between chess pieces
    public void addDiagonalEdges(List<List<Integer>> edges) {
        edges.add(new ArrayList<Integer>() {{
            add(0);
            add(4);
        }});
        edges.add(new ArrayList<Integer>() {{
            add(4);
            add(0);
        }});
        edges.add(new ArrayList<Integer>() {{
            add(2);
            add(4);
        }});
        edges.add(new ArrayList<Integer>() {{
            add(4);
            add(2);
        }});
        edges.add(new ArrayList<Integer>() {{
            add(6);
            add(4);
        }});
        edges.add(new ArrayList<Integer>() {{
            add(4);
            add(6);
        }});
        edges.add(new ArrayList<Integer>() {{
            add(8);
            add(4);
        }});
        edges.add(new ArrayList<Integer>() {{
            add(4);
            add(8);
        }});
    }

    // Returns a flat list of all chess pieces on the board
    public List<ChessPiece> getPoints() {
        if (matrixPoints == null) {
            matrixPoints = getMatrixPoints(); // Initialize the matrix if not already done
        }
        points = new ArrayList<>();
        for (List<ChessPiece> list : matrixPoints) {
            points.addAll(list); // Flatten the 2D matrix into a single list
        }

        return points;
    }
}