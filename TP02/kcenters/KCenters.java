package kcenters;

import algorithms.BruteForce;
import algorithms.FloydWarshall;
import algorithms.KMeans;
import graph.Graph;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import models.Result;

public class KCenters {
    public static void main(String[] args) {
        try {
            String filePath = "entradaspmed/pmed03.txt";
            Graph graph = Graph.fromFile(filePath);
            int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
            int k = graph.getK();

            FloydWarshall.computeShortestPaths(adjacencyMatrix);

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Escolha o método a ser executado:");
                System.out.println("1. KMeans");
                System.out.println("2. BruteForce");
                System.out.print("Digite sua escolha (1 ou 2): ");

                int choice;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Certifique-se de digitar um número inteiro.");
                    return;
                }

                switch (choice) {
                    case 1: {
                        long startTime = System.nanoTime();
                        Result kMeansResult = KMeans.solve(adjacencyMatrix, k);
                        long endTime = System.nanoTime();
                        System.out.println("KMeans - Best Centers: " + kMeansResult.centers);
                        System.out.println("KMeans - Radius: " + kMeansResult.radius);
                        System.out.printf("Tempo de execução do KMeans: %.2f ms%n", (endTime - startTime) / 1e6);
                        break;
                    }
                    case 2: {
                        long startTime = System.nanoTime();
                        Result bruteForceResult = BruteForce.solve(adjacencyMatrix, k);
                        long endTime = System.nanoTime();
                        System.out.println("BruteForce - Best Centers: " + bruteForceResult.centers);
                        System.out.println("BruteForce - Radius: " + bruteForceResult.radius);
                        System.out.printf("Tempo de execução do BruteForce: %.2f ms%n", (endTime - startTime) / 1e6);
                        break;
                    }
                    default: {
                        System.out.println("Escolha inválida. Encerrando o programa.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
}
