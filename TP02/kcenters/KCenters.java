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
        Scanner scanner = new Scanner(System.in);

        // Solicita ao usuário o método desejado
        System.out.println("Escolha o método a ser executado:");
        System.out.println("1. KMeans");
        System.out.println("2. BruteForce");
        System.out.print("Digite sua escolha (1 ou 2): ");

        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Entrada inválida. Certifique-se de digitar um número inteiro.");
            return;
        }

        // Confirma o método escolhido
        if (choice != 1 && choice != 2) {
            System.out.println("Escolha inválida. Encerrando o programa.");
            return;
        }

        System.out.println("Iniciando execução para todas as instâncias...");

        // Executa todas as instâncias do diretório "entradas"
        for (int instance = 1; instance <= 40; instance++) {
            String filePath = String.format("entradaspmed/pmed%02d.txt", instance);
            try {
                Graph graph = Graph.fromFile(filePath);
                int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
                int k = graph.getK();

                FloydWarshall.computeShortestPaths(adjacencyMatrix);

                long startTime = System.nanoTime();
                Result result;

                // Executa o método escolhido
                if (choice == 1) {
                    result = KMeans.solve(adjacencyMatrix, k);
                    System.out.printf("PMED%02d - Método: KMeans, Radius: %d", instance, result.radius);
                } else {
                    result = BruteForce.solve(adjacencyMatrix, k);
                    System.out.printf("PMED%02d - Método: BruteForce, Radius: %d", instance, result.radius);
                }

                long endTime = System.nanoTime();
                System.out.printf(", Tempo: %.2f ms%n", (endTime - startTime) / 1e6);

            } catch (IOException e) {
                System.err.printf("Erro ao processar %s: %s%n", filePath, e.getMessage());
            } catch (OutOfMemoryError e) {
                System.err.printf("Erro de memória ao processar %s. Tente outra abordagem.%n", filePath);
            }
        }
    }
}
