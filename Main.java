import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        
        // Initialize graph and population data using DataLoader.
        Map<String, List<DataLoader.Edge>> graph = DataLoader.getIslandGraph();
        Map<String, Integer> populations = DataLoader.getIslandPopulations();
        Map<String, Map<String, Integer>> resources = DataLoader.getNaturalResources();
        
        // Initialize variables passed as arguments to algorithms.
        String startIsland;
        String resource;
        int numCanoes;
        int canoeCapacity;

        // Setup tracking for recency and skills on each island.
        Map<String, Integer> recentVisits = new HashMap<>(); // Last visit timestamp.
        Map<String, Set<String>> skills = new HashMap<>();   // Skills shared with each island.
        populations.keySet().forEach(island -> {
            recentVisits.put(island, 0);
            skills.put(island, new HashSet<>());
        });

        // Start the leader’s journey from a starting island.
        startIsland = "Hawaii";
        List<String> leaderRoute = Algorithms.leaderRoutePlan(graph, populations, recentVisits, skills, startIsland);

        // Display the route taken by the leader for knowledge sharing.
        System.out.println("Leader's Knowledge Sharing Route:");
        leaderRoute.forEach(System.out::println);        
        
        //Distribute resources from starting island to others.
        resource = "Koa Wood";
        numCanoes = 8;
        canoeCapacity = 10;
        List<String> route = Algorithms.distributeResource(graph, resources, startIsland, resource, numCanoes, canoeCapacity);
        
        // Ensure the resource is available on the start island. 
        resources.get(startIsland).put(resource, 100);

        // Display the distribution route. 
        System.out.println("\nResource Distribution Route:"); 
        route.forEach(System.out::println);


        /*
         * Problem 3: Distributing an island's natural resource across Polynesia
         */
         System.err.println("\nProblem 3: Distributing an island's natural resource across Polynesia");
         // Ensures the graph is the same as the one loaded from DataLoader
         if (graph != DataLoader.getIslandGraph()){
            graph = DataLoader.getIslandGraph();
        }

        // Loads the graph's islands' natural resources, sets a starting island and the number of canoes to be used, and distributes the natural resource
        Map<String, Map<String, Integer>> naturalResources = DataLoader.setNaturalResource();
        startIsland = "Aotearoa";
        numCanoes = 8;
        String naturalResource = naturalResources.get(startIsland).keySet().iterator().next();
        List<String> naturalResourceDistributionRoute = Algorithms.distributeNaturalResource(graph, naturalResources, startIsland, numCanoes);
        
        // Displays the route taken to distribute the natural resource
        System.out.println("Route taken to distribute " + naturalResource + " from " + startIsland + " with " + numCanoes + " canoes:");
        naturalResourceDistributionRoute.forEach(System.out::println);
        System.err.println(naturalResource + " has been distributed across Polynesia\n");
        /* End of Problem 3 */
    }

    // Inner Pair class used within Main and Algorithms.
    public static class Pair<F, S> {
        public F first;
        public S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}
