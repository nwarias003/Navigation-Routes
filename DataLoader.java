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
        populations.put("Hawaii", 100000);
        populations.put("Maui", 50000);
        populations.put("Oahu", 50000);
        populations.put("Kauai", 33000);
        populations.put("Tahiti", 38000);
        populations.put("Austral Islands", 6500);
        populations.put("Marquesas Islands", 30000);
        populations.put("Tuamotu Islands", 5500);
        populations.put("Mangareva", 1500);
        populations.put("Rapa Nui", 8000);
        populations.put("Cook Islands", 8500);
        populations.put("Aotearoa", 110000);
        populations.put("Chatham Islands", 2000);
        populations.put("Tonga", 35000);
        populations.put("Niue", 4500);
        populations.put("Samoa", 40000);
        populations.put("Wallis and Futuna", 6000);
        populations.put("Rotuma", 2800);
        populations.put("Tuvalu", 3500);
        populations.put("Tokelau", 1300);

        return populations;
    }

    /**
     * Generates natural resource data for each island.
     * 
     * @return A Map where each key is an island name, and the value is a map of resources and quantities.
     */
    public static Map<String, Map<String, Integer>> setNaturalResource() {
        Map<String, Map<String,Integer>> naturalResources = new HashMap<>();
        naturalResources.put("Hawaii", setResourceQuantity("Koa Wood", 10000));
        naturalResources.put("Maui", setResourceQuantity("Lokelani Rose", 5000));
        naturalResources.put("Oahu", setResourceQuantity("Pineapple", 8000));
        naturalResources.put("Kauai", setResourceQuantity("Taro", 4000));
        naturalResources.put("Tahiti", setResourceQuantity("Vanilla Bean", 3500));
        naturalResources.put("Austral Islands", setResourceQuantity("Coffee Bean", 1500));
        naturalResources.put("Marquesas Islands", setResourceQuantity("Papaya", 6000));
        naturalResources.put("Tuamotu Islands", setResourceQuantity("Lilikoi", 1000));
        naturalResources.put("Mangareva", setResourceQuantity("Guava", 800));
        naturalResources.put("Rapa Nui", setResourceQuantity("Sweet Potato", 2000));
        naturalResources.put("Cook Islands", setResourceQuantity("Cacao", 1700));
        naturalResources.put("Aotearoa", setResourceQuantity("Silver Fern", 11000));
        naturalResources.put("Chatham Islands", setResourceQuantity("Kukui Nut", 500));
        naturalResources.put("Tonga", setResourceQuantity("Yam", 7000));
        naturalResources.put("Niue", setResourceQuantity("Hibiscus Flower", 1000));
        naturalResources.put("Samoa", setResourceQuantity("Coconut", 8000));
        naturalResources.put("Wallis and Futuna", setResourceQuantity("Banana", 1200));
        naturalResources.put("Rotuma", setResourceQuantity("Kava", 1500));
        naturalResources.put("Tuvalu", setResourceQuantity("Rice", 1200));
        naturalResources.put("Tokelau", setResourceQuantity("Lychee", 500));

        return naturalResources;
    }
    
   public static Map<String, Map<String, Integer>> getNaturalResources() { 
   // Initialize the resources available on each island 
      Map<String, Map<String, Integer>> resources = new HashMap<>(); 
      resources.put("Hawaii", new HashMap<>()); 
      resources.put("Maui", new HashMap<>()); 
      resources.put("Oahu", new HashMap<>()); 
      resources.put("Kauai", new HashMap<>()); 
      resources.put("Tahiti", new HashMap<>()); 
      resources.put("Austral Islands", new HashMap<>());
      resources.put("Marquesas Islands", new HashMap<>());
      resources.put("Tuamotu Islands", new HashMap<>()); 
      resources.put("Mangareva", new HashMap<>()); 
      resources.put("Rapa Nui", new HashMap<>()); 
      resources.put("Cook Islands", new HashMap<>()); 
      resources.put("Aotearoa", new HashMap<>()); 
      resources.put("Chatham Islands", new HashMap<>()); 
      resources.put("Tonga", new HashMap<>()); 
      resources.put("Niue", new HashMap<>()); 
      resources.put("Samoa", new HashMap<>()); 
      resources.put("Wallis and Futuna", new HashMap<>()); 
      resources.put("Rotuma", new HashMap<>()); 
      resources.put("Tuvalu", new HashMap<>()); 
      resources.put("Tokelau", new HashMap<>()); 
      
      return resources;
   }

    // Adds a bidirectional edge between two islands with a given travel time.
    private static void addEdge(Map<String, List<Edge>> graph, String islandA, String islandB, int travelTime) {        
        graph.putIfAbsent(islandA, new ArrayList<>());
        graph.get(islandA).add(new Edge(islandB, travelTime));

        graph.putIfAbsent(islandB, new ArrayList<>());
        graph.get(islandB).add(new Edge(islandA, travelTime));
    }

    // Maps the resource name to the quantity.
    private static Map<String, Integer> setResourceQuantity(String name, int quantity) {
        Map<String, Integer> resources = new HashMap<>();
        resources.put(name, quantity);

        return resources;
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
