package Lab_9.Graph;

import java.util.*;

public class Graph {
    private Map<Node, List<Edge>> adjList = new HashMap<>();

    public void addNode(Node node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node from, Node to, int weight) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());

        // Undirected graph: add edge both ways
        adjList.get(from).add(new Edge(from, to, weight));
        adjList.get(to).add(new Edge(to, from, weight));
    }

    public List<Edge> getEdges(Node node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }

    public Set<Node> getNodes() {
        return adjList.keySet();
    }

    public boolean containsNode(Node node) {
        return adjList.containsKey(node);
    }

    public Map<Node, List<Edge>> getAdjacencyList() {
        return adjList;
    }

    public List<List<Node>> findAllPaths(Node start, Node end) {
        List<List<Node>> allPaths = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        List<Node> path = new ArrayList<>();
        dfsFindPaths(start, end, visited, path, allPaths);
        return allPaths;
    }

    private void dfsFindPaths(Node current, Node end, Set<Node> visited, List<Node> path, List<List<Node>> allPaths) {
        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            allPaths.add(new ArrayList<>(path));
        } else {
            for (Edge edge : adjList.get(current)) {
                Node neighbor = edge.getTo();
                if (!visited.contains(neighbor)) {
                    dfsFindPaths(neighbor, end, visited, path, allPaths);
                }
            }
        }

        path.remove(path.size() - 1);
        visited.remove(current);
    }

    public int calculatePathCost(List<Node> path) {
        int total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node from = path.get(i);
            Node to = path.get(i + 1);
            for (Edge edge : adjList.get(from)) {
                if (edge.getTo().equals(to)) {
                    total += edge.getWeight();
                    break;
                }
            }
        }
        return total;
    }

    public List<Node> reconstructPath(Node start, Node end, Map<Node, Node> predecessors) {
        List<Node> path = new ArrayList<>();
        Node step = end;

        while (step != null && !step.equals(start)) {
            path.add(step);
            step = predecessors.get(step);
        }

        if (step == null) return Collections.emptyList(); // No path found

        path.add(start);
        Collections.reverse(path);
        return path;
    }

    public Map<Node, Integer> dijkstra(Node start, Map<Node, Node> predecessors) {
        Map<Node, Integer> distances = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<Node> visited = new HashSet<>();

        // Initialize distances
        for (Node node : adjList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (visited.contains(current)) continue;
            visited.add(current);

            for (Edge edge : adjList.get(current)) {
                Node neighbor = edge.getTo();
                int newDist = distances.get(current) + edge.getWeight();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    predecessors.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }

        return distances;
    }

}
