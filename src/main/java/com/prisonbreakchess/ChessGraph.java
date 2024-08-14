package com.prisonbreakchess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChessGraph {


    private List<Integer> vertices;
    private List<List<Integer>> edges;
    private HashMap<Integer, List<Integer>> graph;

    public ChessGraph(List<Integer> vertices, List<List<Integer>> edges){
        this.vertices = vertices;
        this.edges = edges;
        graph = new HashMap<>();
        createGraph();
    }

    private void createGraph() {
        for (Integer vertex : vertices) {
            List<Integer> adjacencyVertices = new ArrayList<>();
            for (List<Integer> edge : edges) {
                if (edge.get(0).equals(vertex)){
                    adjacencyVertices.add(edge.get(1));
                }
            }
            graph.put(vertex, adjacencyVertices);
        }
    }

    public List<Integer> getVertices() {
        return vertices;
    }

    public void setVertices(List<Integer> vertices) {
        this.vertices = vertices;
    }

    public List<List<Integer>> getEdges() {
        return edges;
    }

    public void setEdges(List<List<Integer>> edges) {
        this.edges = edges;
    }

    public HashMap<Integer, List<Integer>> getGraph() {
        return graph;
    }
}
