package com.prisonbreakchess;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ChessPiecesFactory {
    private int startPositionX;
    private int startPositionY;
    private int endPositionX;
    private int endPositionY;
    private int intervalX;
    private int intervalY;

    private int numsX;
    private int numsY;
    private double pieceRadius;
    private List<List<ChessPiece>> matrixPoints;
    private List<ChessPiece> points;
    private ChessGraph graph;

    public ChessPiecesFactory(int startPositionX, int startPositionY, int endPositionX, int endPositionY, int numsX, int numsY, double pieceRadius) {
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
        this.endPositionX = endPositionX;
        this.endPositionY = endPositionY;
        this.intervalX = (endPositionX - startPositionX) / (numsX - 1);
        this.intervalY = (endPositionY - startPositionY) / (numsY - 1);
        this.numsX = numsX;
        this.numsY = numsY;
        this.pieceRadius = pieceRadius;
    }

    public List<List<ChessPiece>> getMatrixPoints() {
        matrixPoints = new ArrayList<>();
        int tempY = startPositionY;
        int id = 0;
        for (int i = 0; i < numsY; i++) {
            List<ChessPiece> linePoints = new ArrayList<>();
            int tempX = startPositionX;
            for (int j = 0; j < numsX; j++) {
                ChessPiece chessPiece = new ChessPiece(id, tempX, tempY, pieceRadius, Color.TRANSPARENT);
                linePoints.add(chessPiece);
                id++;
                tempX += intervalX;
            }
            matrixPoints.add(linePoints);
            tempY += intervalY;
        }
        return matrixPoints;
    }

    public void drawChessBoard(GraphicsContext gc) {
        int width = endPositionX - startPositionX;
        int height = endPositionY - startPositionY;
        int widthGap = width / (numsX - 1);
        int heightGap = height / (numsY - 1);
        int temp = startPositionX;
        for (int i = 0; i < numsX; i++) {
            gc.strokeLine(temp, startPositionY, temp, endPositionY);
            temp += widthGap;
        }
        temp = startPositionY;
        for (int i = 0; i < numsY; i++) {
            gc.strokeLine(startPositionX, temp, endPositionX, temp);
            temp += heightGap;
        }
        // Draw diagonal lines
        gc.strokeLine(startPositionX, startPositionY, endPositionX, endPositionY);
        gc.strokeLine(endPositionX, startPositionY, startPositionX, endPositionY);
    }

    public ChessGraph getGraph() {
        List<Integer> vertices = new ArrayList<>();
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < matrixPoints.size(); i++) {
            List<ChessPiece> list = matrixPoints.get(i);
            for (int j = 0; j < list.size(); j++) {
                vertices.add(list.get(j).getId());
                if (j == 0) {
                    List<Integer> edge1 = new ArrayList<>();
                    edge1.add(list.get(j).getId());
                    edge1.add(list.get(j + 1).getId());
                    edges.add(edge1);
                    addVerticalEdges(edges, i, j);
                } else if (j < list.size() - 1) {
                    List<Integer> edge1 = new ArrayList<>();
                    edge1.add(list.get(j).getId());
                    edge1.add(list.get(j - 1).getId());
                    edges.add(edge1);
                    List<Integer> edge2 = new ArrayList<>();
                    edge2.add(list.get(j).getId());
                    edge2.add(list.get(j + 1).getId());
                    edges.add(edge2);
                    addVerticalEdges(edges, i, j);

                } else if (j == list.size() - 1) {
                    List<Integer> edge = new ArrayList<>();
                    edge.add(list.get(j).getId());
                    edge.add(list.get(j - 1).getId());
                    edges.add(edge);
                    addVerticalEdges(edges, i, j);
                }
            }
        }
        addDiagonalEdges(edges);
        graph = new ChessGraph(vertices, edges);
        System.out.println(graph.toString());
        return graph;
    }

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

    public List<ChessPiece> getPoints() {
        if (matrixPoints == null) {
            matrixPoints = getMatrixPoints();
        }
        points = new ArrayList<>();
        for (List<ChessPiece> list : matrixPoints) {
            points.addAll(list);
        }

        return points;
    }
}
