import java.io.*;
import java.util.*;

public class Kcenters {
    private static final int INF = Integer.MAX_VALUE;

    // Classe Graph
    static class Graph {
        private int[][] adjacencyMatrix;

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
            adjacencyMatrix[v][u] = weight; // Grafos não direcionados
        }

        public int[][] getAdjacencyMatrix() {
            return adjacencyMatrix;
        }
    }

    // Classe FloydWarshall
    static class FloydWarshall {
        public static void computeShortestPaths(int[][] graph) {
            int n = graph.length;

            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (graph[i][k] != INF && graph[k][j] != INF) {
                            graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                        }
                    }
                }
            }
        }
    }

    // Classe GraphFileReader com alterações
    static class GraphFileReader {
        static class ResultData {
            Graph graph;
            int k;
    
            ResultData(Graph graph, int k) {
                this.graph = graph;
                this.k = k;
            }
        }
    
        public static ResultData readGraph(String filePath) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String firstLine = reader.readLine();
    
            if (firstLine == null || firstLine.trim().isEmpty()) {
                throw new IOException("O arquivo está vazio ou não possui uma linha inicial válida.");
            }
    
            String[] firstLineParts = firstLine.trim().split(" ");
            if (firstLineParts.length < 3) {
                throw new IOException("Formato inválido na primeira linha do arquivo.");
            }
    
            int numNodes = Integer.parseInt(firstLineParts[0]);
            int k = Integer.parseInt(firstLineParts[2]);
            Graph graph = new Graph(numNodes);
    
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
    
                String[] parts = line.trim().split(" ");
                int u = Integer.parseInt(parts[0]) - 1;
                int v = Integer.parseInt(parts[1]) - 1;
                int weight = Integer.parseInt(parts[2]);
                graph.addEdge(u, v, weight);
            }
    
            reader.close();
            return new ResultData(graph, k);
        }
    }
    
    // Classe Result
    static class Result {
        public List<Integer> centers;
        public int radius;

        public Result(List<Integer> centers, int radius) {
            this.centers = centers;
            this.radius = radius;
        }
    }

    // Classe BruteForce
// Classe BruteForce
static class BruteForce {
    private static List<Integer> currentCombination = new ArrayList<>();
    private static int bestRadius = INF;
    private static List<Integer> bestCenters = null;

    public static Result solve(int[][] graph, int k) {
        // Inicializa as variáveis para buscar a melhor solução
        bestRadius = INF;
        bestCenters = null;
        // Gera combinações dinamicamente e avalia a solução
        generateCombinations(graph, graph.length, k, 0);
        // Retorna o melhor resultado encontrado
        return new Result(bestCenters, bestRadius);
    }

    private static void generateCombinations(int[][] graph, int n, int k, int start) {
        if (currentCombination.size() == k) {
            // Calcula o raio da combinação atual
            int radius = calculateRadius(graph, currentCombination);
            // Atualiza a melhor solução se o raio atual for menor
            if (radius < bestRadius) {
                bestRadius = radius;
                bestCenters = new ArrayList<>(currentCombination);
            }
            return;
        }

        // Gera combinações dinamicamente
        for (int i = start; i < n; i++) {
            currentCombination.add(i);
            generateCombinations(graph, n, k, i + 1);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private static int calculateRadius(int[][] graph, List<Integer> centers) {
        int maxRadius = 0;

        for (int i = 0; i < graph.length; i++) {
            int minDist = INF;
            for (int center : centers) {
                minDist = Math.min(minDist, graph[i][center]);
            }
            maxRadius = Math.max(maxRadius, minDist);
        }

        return maxRadius;
    }
}


    // Classe KMeans
    public static class KMeans {
        private static final int INF = Integer.MAX_VALUE;

        public static Result solve(int[][] graph, int k) {
            int numNodes = graph.length;

            List<Integer> centroids = initializeCentroids(numNodes, k);
            Map<Integer, List<Integer>> clusters = assignClusters(graph, k, centroids);
            int radius = findRadius(graph, clusters);
            Map<Integer, List<Integer>> bestClusters = clusters;

            while (true) {
                List<Integer> newCentroids = updateCentroids(graph, clusters);
                clusters = assignClusters(graph, k, newCentroids);
                int newRadius = findRadius(graph, clusters);

                if (newCentroids.equals(centroids)) {
                    break;
                } else {
                    centroids = newCentroids;
                    bestClusters = clusters;
                    radius = newRadius;
                }
            }

            return new Result(new ArrayList<>(bestClusters.keySet()), radius);
        }

        private static List<Integer> initializeCentroids(int numNodes, int k) {
            List<Integer> centroids = new ArrayList<>();
            Random random = new Random();

            while (centroids.size() < k) {
                int nextCentroid = random.nextInt(numNodes);
                if (!centroids.contains(nextCentroid)) {
                    centroids.add(nextCentroid);
                }
            }

            return centroids;
        }

        private static Map<Integer, List<Integer>> assignClusters(int[][] graph, int k, List<Integer> centroids) {
            Map<Integer, List<Integer>> clusters = new HashMap<>();
            for (int centroid : centroids) {
                clusters.put(centroid, new ArrayList<>());
            }

            for (int i = 0; i < graph.length; i++) {
                int minDist = INF;
                int closestCentroid = -1;

                for (int j = 0; j < k; j++) {
                    int centroid = centroids.get(j);
                    if (graph[i][centroid] < minDist) {
                        minDist = graph[i][centroid];
                        closestCentroid = centroid;
                    }
                }

                clusters.get(closestCentroid).add(i);
            }

            return clusters;
        }

        private static int findRadius(int[][] graph, Map<Integer, List<Integer>> clusters) {
            int maxRadius = 0;

            for (Map.Entry<Integer, List<Integer>> entry : clusters.entrySet()) {
                int centroid = entry.getKey();
                List<Integer> cluster = entry.getValue();

                int clusterRadius = 0;
                for (int node : cluster) {
                    clusterRadius = Math.max(clusterRadius, graph[centroid][node]);
                }

                maxRadius = Math.max(maxRadius, clusterRadius);
            }

            return maxRadius;
        }

        private static List<Integer> updateCentroids(int[][] graph, Map<Integer, List<Integer>> clusters) {
            List<Integer> newCentroids = new ArrayList<>();

            for (Map.Entry<Integer, List<Integer>> entry : clusters.entrySet()) {
                List<Integer> cluster = entry.getValue();
                int minAvgDist = INF;
                int newCentroid = -1;

                for (int node : cluster) {
                    int totalDist = 0;
                    for (int other : cluster) {
                        totalDist += graph[node][other];
                    }

                    int avgDist = totalDist / cluster.size();
                    if (avgDist < minAvgDist) {
                        minAvgDist = avgDist;
                        newCentroid = node;
                    }
                }

                newCentroids.add(newCentroid);
            }

            return newCentroids;
        }
    }

    // Método principal
    public static void main(String[] args) {
        try {
            String filePath = "entradaspmed/pmed02.txt";
    
            // Lê o grafo e o valor de k diretamente do arquivo usando GraphFileReader
            GraphFileReader.ResultData resultData = GraphFileReader.readGraph(filePath);
            Graph graph = resultData.graph;
            int k = resultData.k;
    
            int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
            FloydWarshall.computeShortestPaths(adjacencyMatrix);
    
            Scanner scanner = new Scanner(System.in);
            System.out.println("Escolha o método a ser executado:");
            System.out.println("1. KMeans");
            System.out.println("2. BruteForce");
            System.out.print("Digite sua escolha (1 ou 2): ");
            int choice = scanner.nextInt();
    
            if (choice == 1) {
                long startTime = System.nanoTime();
                Result kMeansResult = KMeans.solve(adjacencyMatrix, k);
                long endTime = System.nanoTime();
                System.out.println("KMeans - Best Centers: " + kMeansResult.centers);
                System.out.println("KMeans - Radius: " + kMeansResult.radius);
                System.out.printf("Tempo de execução do KMeans: %.2f ms%n", (endTime - startTime) / 1e6);
            } else if (choice == 2) {
                long startTime = System.nanoTime();
                Result bruteForceResult = BruteForce.solve(adjacencyMatrix, k);
                long endTime = System.nanoTime();
                System.out.println("BruteForce - Best Centers: " + bruteForceResult.centers);
                System.out.println("BruteForce - Radius: " + bruteForceResult.radius);
                System.out.printf("Tempo de execução do BruteForce: %.2f ms%n", (endTime - startTime) / 1e6);
            } else {
                System.out.println("Escolha inválida. Encerrando o programa.");
            }
    
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Certifique-se de digitar números inteiros.");
        }
      } 
    }
