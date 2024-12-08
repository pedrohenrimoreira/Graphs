package algorithms;
import java.util.ArrayList;
import java.util.List;
import models.Result;

public class BruteForce {
    private static final int INF = Integer.MAX_VALUE;
    private static final List<Integer> currentCombination = new ArrayList<>();
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

        for (int[] graph1 : graph) {
            int minDist = INF;
            for (int center : centers) {
                minDist = Math.min(minDist, graph1[center]);
            }
            maxRadius = Math.max(maxRadius, minDist);
        }

        return maxRadius;
    }
}
