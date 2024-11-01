import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {

    /**
     * Generates a directed graph where islands are nodes and routes are weighted edges.
     * 
     * @return A Map representing the island graph where each island has a list of connected edges.
     */
    public static Map<String, List<Edge>> getIslandGraph() {
        Map<String, List<Edge>> graph = new HashMap<>();
        
        // Adding edges to represent the routes between islands and their travel times.
        addEdge(graph, "Hawaii", "Tahiti", 15);
        addEdge(graph, "Tahiti", "Rapa Nui", 20);
        addEdge(graph, "Rapa Nui", "Hawaii", 25);
        
        // Going to add more edges here.

        
        return graph;
    }

    /**
     * Generates population data for each island.
     * 
     * @return A Map where each key is an island name, and the value is the population size.
     */
    public static Map<String, Integer> getIslandPopulations() {
        Map<String, Integer> populations = new HashMap<>();
        populations.put("Hawaii", 1500000);
        populations.put("Tahiti", 350000);
        populations.put("Rapa Nui", 8000);
        
        // Going to add more islands.
        
        return populations;
    }

    private static void addEdge(Map<String, List<Edge>> graph, String from, String to, int travelTime) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.get(from).add(new Edge(to, travelTime));
    }

    public static class Edge {
        String destination;
        int travelTime;

        Edge(String destination, int travelTime) {
            this.destination = destination;
            this.travelTime = travelTime;
        }
    }
}
