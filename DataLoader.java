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
        addEdge(graph, "Hawaii", "Maui", 2);
        addEdge(graph, "Hawaii", "Oahu", 3);
        addEdge(graph, "Hawaii", "Kauai", 5);
        addEdge(graph, "Maui", "Oahu", 1);
        addEdge(graph, "Maui", "Kauai", 4);
        addEdge(graph, "Oahu", "Kauai", 2);
        addEdge(graph, "Hawaii", "Tahiti", 42);
        addEdge(graph, "Hawaii", "Marquesas Islands", 38);
        addEdge(graph, "Aotearoa", "Chatham Islands", 7);
        addEdge(graph, "Aotearoa", "Cook Islands", 30);
        addEdge(graph, "Aotearoa", "Tonga", 22);
        addEdge(graph, "Rapa Nui", "Mangareva", 26);
        addEdge(graph, "Tahiti", "Marquesas Islands", 14);
        addEdge(graph, "Tahiti", "Tuamotu Islands", 3);
        addEdge(graph, "Tahiti", "Austral Islands", 7);
        addEdge(graph, "Marquesas Islands", "Tuamotu Islands", 9);
        addEdge(graph, "Tuamotu Islands", "Mangareva", 15);
        addEdge(graph, "Tahiti", "Cook Islands", 11);
        addEdge(graph, "Tahiti", "Samoa", 22);
        addEdge(graph, "Cook Islands", "Niue", 9);
        addEdge(graph, "Niue", "Tonga", 4);
        addEdge(graph, "Niue", "Samoa", 6);
        addEdge(graph, "Tonga", "Samoa", 9);
        addEdge(graph, "Tonga", "Wallis and Futuna", 7);
        addEdge(graph, "Samoa", "Wallis and Futuna", 6);
        addEdge(graph, "Samoa", "Tokelau", 5);
        addEdge(graph, "Wallis and Futuna", "Rotuma", 7);
        addEdge(graph, "Rotuma", "Tuvalu", 7);
        addEdge(graph, "Tuvalu", "Tokelau", 5);

        
        return graph;
    }

    /**
     * Generates population data for each island.
     * 
     * @return A Map where each key is an island name, and the value is the population size.
     */
    public static Map<String, Integer> getIslandPopulations() {
        Map<String, Integer> populations = new HashMap<>();
        populations.put("Samoa", 40000);
        populations.put("Cook Islands", 8500);
        populations.put("Rapa Nui", 8000);
        populations.put("Tahiti", 38000);
        populations.put("Tuamotu Islands", 5500);
        populations.put("Mangareva", 1500);
        populations.put("Marquesas Islands", 30000);
        populations.put("Austral Islands", 6500);
        populations.put("Oahu", 50000);
        populations.put("Hawaii", 100000);
        populations.put("Maui", 50000);
        populations.put("Kauai", 33000);
        populations.put("Aotearoa", 110000);
        populations.put("Chatham Islands", 2000);
        populations.put("Niue", 4500);
        populations.put("Rotuma", 2800);
        populations.put("Tokelau", 1300);
        populations.put("Tonga", 35000);
        populations.put("Tuvalu", 3500);
        populations.put("Wallis and Futuna", 6000);
        
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
