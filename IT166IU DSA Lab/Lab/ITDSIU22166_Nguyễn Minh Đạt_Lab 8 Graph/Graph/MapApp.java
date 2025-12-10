package Lab_9.Graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapApp {
    public static void main(String[] args) {
        Graph graph = new Graph();

        // Nodes
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");
        Node G = new Node("G");
        Node H = new Node("H");
        Node I = new Node("I");
        Node J = new Node("J");
        Node K = new Node("K");
        Node L = new Node("L");

        // Add nodes
        Node[] nodes = {A,B,C,D,E,F,G,H,I,J,K,L};
        for (Node node : nodes) {
            graph.addNode(node);
        }

        // Add edges from the diagram
        graph.addEdge(A, B, 6);
        graph.addEdge(A, G, 10);
        graph.addEdge(B, C, 11);
        graph.addEdge(B, D, 14);
        graph.addEdge(B, G, 12);
        graph.addEdge(C, E, 6);
        graph.addEdge(C, F, 3);
        graph.addEdge(C, G, 12);
        graph.addEdge(D, E, 4);
        graph.addEdge(D, K, 15);
        graph.addEdge(E, H, 12);
        graph.addEdge(E, F, 6);
        graph.addEdge(F, G, 8);
        graph.addEdge(F, H, 16);
        graph.addEdge(F, I, 6);
        graph.addEdge(H, I, 13);
        graph.addEdge(H, J, 18);
        graph.addEdge(H, K, 12);
        graph.addEdge(H, L, 20);
        graph.addEdge(I, L, 17);
        graph.addEdge(J, K, 9);

        // ---- Task 4: Paths from A to K
        System.out.println("\n--- Task 4 ---");
        List<List<Node>> allPathsAK = graph.findAllPaths(A, K);
        System.out.println("Total number of paths from A to K: " + allPathsAK.size());

        // Find shortest and longest path by number of nodes
        List<Node> shortest = null, longest = null;

        for (List<Node> path : allPathsAK) {
            if (shortest == null || path.size() < shortest.size()) {
                shortest = path;
            }
            if (longest == null || path.size() > longest.size()) {
                longest = path;
            }
        }

        // Print shortest path
        System.out.println("\nPath with the smallest number of nodes:");
        for (Node node : shortest) System.out.print(node.getName() + " ");
        System.out.println("\nCost: " + graph.calculatePathCost(shortest));

        // Print longest path
        System.out.println("\nPath with the largest number of nodes:");
        for (Node node : longest) System.out.print(node.getName() + " ");
        System.out.println("\nCost: " + graph.calculatePathCost(longest));

        // ---- Task 5: Shortest path from A to J
        System.out.println("\n--- Task 5 ---");
        Map<Node, Node> predAJ = new HashMap<>();
        Map<Node, Integer> distAJ = graph.dijkstra(A, predAJ);
        List<Node> pathAJ = graph.reconstructPath(A, J, predAJ);
        System.out.println("Shortest path from A to J:");
        for (Node node : pathAJ) System.out.print(node.getName() + " ");
        System.out.println("\nTotal cost: " + distAJ.get(J));

        // ---- Task 5: Shortest path from B to L
        Map<Node, Node> predBL = new HashMap<>();
        Map<Node, Integer> distBL = graph.dijkstra(B, predBL);
        List<Node> pathBL = graph.reconstructPath(B, L, predBL);
        System.out.println("\nShortest path from B to L:");
        for (Node node : pathBL) System.out.print(node.getName() + " ");
        System.out.println("\nTotal cost: " + distBL.get(L));
    }
}
