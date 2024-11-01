import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        
        // Initialize graph and population data using DataLoader.
        Map<String, List<DataLoader.Edge>> graph = DataLoader.getIslandGraph();
        Map<String, Integer> populations = DataLoader.getIslandPopulations();

        // Setup tracking for recency and skills on each island.
        Map<String, Integer> recentVisits = new HashMap<>(); // Last visit timestamp.
        Map<String, Set<String>> skills = new HashMap<>();   // Skills shared with each island.
        populations.keySet().forEach(island -> {
            recentVisits.put(island, 0);
            skills.put(island, new HashSet<>());
        });

        // Start the leader’s journey from a starting island.
        String startIsland = "Hawaii";
        List<String> leaderRoute = Algorithms.leaderRoutePlan(graph, populations, recentVisits, skills, startIsland);

        // Display the route taken by the leader for knowledge sharing.
        System.out.println("Leader's Knowledge Sharing Route:");
        leaderRoute.forEach(System.out::println);
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
