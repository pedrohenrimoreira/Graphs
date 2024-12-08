# K-Centers Algorithm Project

Este projeto implementa soluções para o problema de **K-Centers**, utilizando diferentes algoritmos para determinar os melhores centros em um grafo, minimizando o raio máximo entre os nós e os centros escolhidos.

## 📋 Funcionalidades

- **Leitura de grafos a partir de arquivos de entrada.**
- **Execução de dois algoritmos principais para resolver o problema de K-Centers:**
  - **K-Means**: Heurística eficiente para grandes grafos.
  - **Brute Force**: Método exaustivo que garante a solução ótima.
- **Cálculo de menores caminhos usando o algoritmo de Floyd-Warshall.**
- **Exibição do tempo de execução para cada método.**

---

## 📂 Estrutura do Projeto

```plaintext
TP02/
├── algorithms/
│   ├── BruteForce.java
│   ├── FloydWarshall.java
│   └── KMeans.java
├── graph/
│   └── Graph.java
├── models/
│   └── Result.java
├── io/
│   └── GraphFileReader.java
└── kcenters/
    └── KCenters.java
```

## Descrição dos Módulos
- **algorithms/: Implementa os algoritmos necessários para resolver o problema de K-Centers.**
- **graph/: Contém a classe Graph, responsável pela estruturação dos grafos.**
- **models/: Modelos de dados como o Result, utilizado para armazenar os resultados dos algoritmos.**
- **io/: Manipulação de entrada e saída, como a leitura de grafos de arquivos.**
- **kcenters/: Contém o ponto de entrada principal do programa (KCenters).**

## 🚀 Como executar

- **Clone o Repositório**
-  - git clone https://github.com/seu-usuario/k-centers.git
-  - cd k-centers
- **Compile o projeto**
-  - javac -d out $(find . -name "*.java")
- **Execute o programa**
-  - java -cp out kcenters.KCenters
- **Siga as instruções no terminal**
-  - Escolha o método (1 para KMeans, 2 para BruteForce).
-  - Visualize os resultados e o tempo de execução.
  

## 🧪 Exemplo de Execução

Escolha o método a ser executado:
1. KMeans
2. BruteForce
Digite sua escolha (1 ou 2): 1
KMeans - Best Centers: [1, 3]
KMeans - Radius: 4
Tempo de execução do KMeans: 12.34 ms

## 📝 Observações

- Desempenho: Use o KMeans para grafos grandes devido à complexidade computacional do BruteForce.
- Erros: Certifique-se de que o arquivo de entrada esteja no formato correto.

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

## 🧑‍💻 Autor
- Bernardo D'Ávila
- Pedro Henrique Moreira
- Gabriel Fernandes Azevedo
