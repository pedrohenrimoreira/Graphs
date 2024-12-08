package algorithms;

import java.util.ArrayList;
import java.util.List;
import models.Result;

public class BruteForce {
    private static final int INF = Integer.MAX_VALUE;
    private static List<Integer> currentCombination = new ArrayList<>();
    private static int bestRadius = INF;
    private static List<Integer> bestCenters = null;

    public static Result solve(int[][] graph, int k) {
        bestRadius = INF;
        bestCenters = null;
        generateCombinations(graph, graph.length, k, 0);
        return new Result(bestCenters, bestRadius);
    }

    private static void generateCombinations(int[][] graph, int n, int k, int start) {
        if (currentCombination.size() == k) {
            int radius = calculateRadius(graph, currentCombination);
            if (radius < bestRadius) {
                bestRadius = radius;
                bestCenters = new ArrayList<>(currentCombination);
            }
            return;
        }

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
