package algorithms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import models.Result;

public class KMeans {
    private static final int INF = Integer.MAX_VALUE;

    public static Result solve(int[][] graph, int k) {
        int numNodes = graph.length;
        List<Integer> centroids = initializeCentroids(numNodes, k);
        Map<Integer, List<Integer>> clusters = assignClusters(graph, k, centroids);
        int radius = findRadius(graph, clusters);

        while (true) {
            List<Integer> newCentroids = updateCentroids(graph, clusters);
            if (newCentroids.equals(centroids)) break;
            centroids = newCentroids;
            clusters = assignClusters(graph, k, centroids);
            radius = findRadius(graph, clusters);
        }

        return new Result(new ArrayList<>(centroids), radius);
    }

    private static List<Integer> initializeCentroids(int numNodes, int k) {
        List<Integer> centroids = new ArrayList<>();
        Random random = new Random();

        while (centroids.size() < k) {
            int nextCentroid = random.nextInt(numNodes);
            if (!centroids.contains(nextCentroid)) centroids.add(nextCentroid);
        }

        return centroids;
    }

    private static Map<Integer, List<Integer>> assignClusters(int[][] graph, int k, List<Integer> centroids) {
        Map<Integer, List<Integer>> clusters = new HashMap<>();
        for (int centroid : centroids) clusters.put(centroid, new ArrayList<>());

        for (int i = 0; i < graph.length; i++) {
            int minDist = INF;
            int closestCentroid = -1;

            for (int centroid : centroids) {
                if (graph[i][centroid] < minDist) {
                    minDist = graph[i][centroid];
                    closestCentroid = centroid;
                }
            }

            clusters.get(closestCentroid).add(i);
        }

        return clusters;
    }

    private static List<Integer> updateCentroids(int[][] graph, Map<Integer, List<Integer>> clusters) {
        List<Integer> newCentroids = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : clusters.entrySet()) {
            List<Integer> cluster = entry.getValue();
            int minAvgDist = INF;
            int bestNode = -1;

            for (int candidate : cluster) {
                int totalDist = 0;
                for (int node : cluster) totalDist += graph[candidate][node];
                int avgDist = totalDist / cluster.size();
                if (avgDist < minAvgDist) {
                    minAvgDist = avgDist;
                    bestNode = candidate;
                }
            }

            newCentroids.add(bestNode);
        }
        return newCentroids;
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
}
