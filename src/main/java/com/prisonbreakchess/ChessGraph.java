package com.prisonbreakchess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The ChessGraph class represents a graph structure used to model the connections
 * between chess pieces on the board. It is used to determine valid moves and paths
 * for the pieces.
 */
public class ChessGraph {

    // List of vertices (nodes) in the graph, representing chess piece positions
    private List<Integer> vertices;

    // List of edges (connections) between vertices, representing valid moves
    private List<List<Integer>> edges;

    // Adjacency list representation of the graph, mapping each vertex to its connected vertices
    private HashMap<Integer, List<Integer>> graph;

    /**
     * Constructor to initialize the ChessGraph with a list of vertices and edges.
     *
     * @param vertices The list of vertices (chess piece positions) in the graph.
     * @param edges The list of edges (connections between positions) in the graph.
     */
    public ChessGraph(List<Integer> vertices, List<List<Integer>> edges) {
        this.vertices = vertices;
        this.edges = edges;
        graph = new HashMap<>();
        createGraph(); // Build the adjacency list representation of the graph
    }

    /**
     * Creates the graph by constructing an adjacency list from the provided vertices and edges.
     * Each vertex is mapped to a list of its adjacent vertices (connected positions).
     */
    private void createGraph() {
        for (Integer vertex : vertices) {
            List<Integer> adjacencyVertices = new ArrayList<>();
            // Find all edges where the current vertex is the starting point
            for (List<Integer> edge : edges) {
                if (edge.get(0).equals(vertex)) {
                    adjacencyVertices.add(edge.get(1)); // Add the connected vertex
                }
            }
            graph.put(vertex, adjacencyVertices); // Map the vertex to its adjacent vertices
        }
    }

    /**
     * Returns the list of vertices in the graph.
     *
     * @return The list of vertices.
     */
    public List<Integer> getVertices() {
        return vertices;
    }

    /**
     * Sets the list of vertices in the graph.
     *
     * @param vertices The new list of vertices.
     */
    public void setVertices(List<Integer> vertices) {
        this.vertices = vertices;
    }

    /**
     * Returns the list of edges in the graph.
     *
     * @return The list of edges.
     */
    public List<List<Integer>> getEdges() {
        return edges;
    }

    /**
     * Sets the list of edges in the graph.
     *
     * @param edges The new list of edges.
     */
    public void setEdges(List<List<Integer>> edges) {
        this.edges = edges;
    }

    /**
     * Returns the adjacency list representation of the graph.
     *
     * @return The HashMap representing the graph, where each key is a vertex
     *         and the value is a list of adjacent vertices.
     */
    public HashMap<Integer, List<Integer>> getGraph() {
        return graph;
    }
}