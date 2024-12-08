package graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Graph {
    public static final int INF = Integer.MAX_VALUE;
    private final int[][] adjacencyMatrix;
    private int k; // Número de clusters, agora parte da classe Graph

    public Graph(int numNodes) {
        adjacencyMatrix = new int[numNodes][numNodes];
        for (int[] row : adjacencyMatrix) {
            Arrays.fill(row, INF);
        }
        for (int i = 0; i < numNodes; i++) {
            adjacencyMatrix[i][i] = 0;
        }
    }

    public void addEdge(int u, int v, int weight) {
        adjacencyMatrix[u][v] = weight;
        adjacencyMatrix[v][u] = weight;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int getK() {
        return k;
    }

    public static Graph fromFile(String filePath) throws IOException {
        Graph graph;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();

            if (firstLine == null || firstLine.trim().isEmpty())    
                throw new IOException("O arquivo está vazio ou não possui uma linha inicial válida.");

            String[] firstLineParts = firstLine.trim().split(" ");

            if (firstLineParts.length < 3)  
                throw new IOException("Formato inválido na primeira linha do arquivo.");
                
            int numNodes = Integer.parseInt(firstLineParts[0]);
            int k = Integer.parseInt(firstLineParts[2]);
            graph = new Graph(numNodes);
            graph.k = k;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.trim().split(" ");
                int u = Integer.parseInt(parts[0]) - 1;
                int v = Integer.parseInt(parts[1]) - 1;
                int weight = Integer.parseInt(parts[2]);
                graph.addEdge(u, v, weight);
            }
        }
        return graph;
    }
}
