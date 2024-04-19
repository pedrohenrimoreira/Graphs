import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class Graph {
    int size;
    List<List<Integer>> adj;
    int time; // Variável global para manter o tempo de descoberta

    public Graph(int s) {
        size = s;
        adj = new ArrayList<>();
        for (int i = 0; i < s; i++) { 
            adj.add(new ArrayList<>());
        }
    }

    private void DFS(int u, boolean visited[], int disc[], int low[], int parent[], boolean ap[]) {
        int children = 0;
        visited[u] = true;
        disc[u] = low[u] = ++time;
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                children++;
                parent[v] = u;
                DFS(v, visited, disc, low, parent, ap);
                low[u] = Math.min(low[u], low[v]);
                if ((parent[u] == -1 && children > 1) || (parent[u] != -1 && low[v] >= disc[u])) {
                    ap[u] = true;
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    public List<Integer> findArticulationPoints() {
        boolean visited[] = new boolean[size];
        int disc[] = new int[size];
        int low[] = new int[size];
        int parent[] = new int[size];
        boolean ap[] = new boolean[size];

        for (int i = 0; i < size; i++) {
            parent[i] = -1;
            visited[i] = false;
            ap[i] = false;
        }

        for (int i = 0; i < size; i++) {
            if (!visited[i]) {
                DFS(i, visited, disc, low, parent, ap);
            }
        }

        List<Integer> articulationPoints = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (ap[i]) {
                articulationPoints.add(i);
            }
        }

        return articulationPoints;
    }

    // Adicionar na classe Graph
    public List<List<Integer>> tarjan() {
    int index = 0;
    int[] indexes = new int[size];
    int[] lowLink = new int[size];
    boolean[] onStack = new boolean[size];
    Stack<Integer> stack = new Stack<>();
    List<List<Integer>> components = new ArrayList<>();

    for (int i = 0; i < size; i++) {
        if (indexes[i] == 0) {
            strongConnect(i, index, indexes, lowLink, onStack, stack, components);
        }
    }
    return components;
    }

    private void strongConnect(int v, int index, int[] indexes, int[] lowLink, boolean[] onStack, Stack<Integer> stack, List<List<Integer>> components) {
    indexes[v] = lowLink[v] = ++index;
    stack.push(v);
    onStack[v] = true;

    for (int w : adj.get(v)) {
        if (indexes[w] == 0) {
            strongConnect(w, index, indexes, lowLink, onStack, stack, components);
            lowLink[v] = Math.min(lowLink[v], lowLink[w]);
        } else if (onStack[w]) {
            lowLink[v] = Math.min(lowLink[v], indexes[w]);
        }
    }

    if (lowLink[v] == indexes[v]) {
        List<Integer> component = new ArrayList<>();
        int w;
        do {
            w = stack.pop();
            onStack[w] = false;
            component.add(w);
        } while (w != v);
        components.add(component);
    }
    }

    public boolean hasTwoDisjointPaths() {
        throw new UnsupportedOperationException("Unimplemented method 'hasTwoDisjointPaths'");
    }
}

public class Main {
    public static void main(String[] args) {
        boolean debug = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insira o nome do arquivo");
        String fileName = scanner.nextLine();

        System.out.println("Insira o vértice");
        int root = scanner.nextInt();

        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            int vertexAmt = fileScanner.nextInt();
            int edgeAmt = fileScanner.nextInt();

            if (debug) {
                System.out.println(vertexAmt + " " + edgeAmt);
            }

            Graph graph = new Graph(vertexAmt + 1);

            while (fileScanner.hasNext()) {
                int src = fileScanner.nextInt();
                int dest = fileScanner.nextInt();
                graph.adj.get(src - 1).add(dest - 1);
                graph.adj.get(dest - 1).add(src - 1); // Adiciona para ambos os lados para um grafo não direcionado
            }

            // Sorting vertices
            for (List<Integer> list : graph.adj) {
                Collections.sort(list);
            }

            // Print tree edges
            System.out.println("----------------------------------");
            System.out.println("ARESTAS ARVORE");
            for (int i = 0; i < graph.adj.size(); i++) {
                for (int v : graph.adj.get(i)) {
                    if (i < v) {
                        System.out.print("(" + (i + 1) + "-" + (v + 1) + ") ");
                    }
                }
            }
            System.out.println("\n----------------------------------");

            // Dentro do Main após a leitura do grafo
            System.out.println("Verificando dois caminhos disjuntos para cada par de vértices:");
            System.out.println(graph.hasTwoDisjointPaths() ? "Existem dois caminhos disjuntos para cada par de vértices." : "Não existem dois caminhos disjuntos para cada par de vértices.");

            System.out.println("Componentes fortemente conectados:");
            List<List<Integer>> scc = graph.tarjan();
            for (List<Integer> component : scc) {
                System.out.println(component);
            }


            List<Integer> articulationPoints = graph.findArticulationPoints();
            System.out.println("Pontos de articulação encontrados: " + articulationPoints.toString());

            scanner.close();
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + fileName);
        }
    }
}


