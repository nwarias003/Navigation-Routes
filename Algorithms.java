import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

public class Algorithms {

    /**
     * Modified Dijkstra’s algorithm to calculate shortest paths while factoring in population.
     *
     * @param graph The island graph.
     * @param populations A Map with island populations.
     * @param startIsland The starting point for calculating shortest paths.
     * @param alpha A weighting factor for prioritizing population in travel.
     * @return A Map where each island's shortest distance from the start is stored.
     */
    public static Map<String, Integer> shortestPath(Map<String, List<DataLoader.Edge>> graph, Map<String, Integer> populations, String startIsland, double alpha) {
        Map<String, Integer> shortestDistances = new HashMap<>(); // Stores shortest distances.
        PriorityQueue<Main.Pair<String, Double>> queue = new PriorityQueue<>(Comparator.comparingDouble(pair -> pair.second)); // Min-heap to process nodes by shortest path.

        // Initialize all distances as infinity, except for the start island.
        graph.keySet().forEach(node -> shortestDistances.put(node, Integer.MAX_VALUE));
        shortestDistances.put(startIsland, 0);
        queue.add(new Main.Pair<>(startIsland, 0.0));

        int maxPopulation = Collections.max(populations.values()); // Find the maximum population to normalize.

        // Processing each island in the priority queue.
        while (!queue.isEmpty()) {
            Main.Pair<String, Double> current = queue.poll();
            String currentNode = current.first;
            double currentDistance = current.second;

            // Skip if a shorter path to this node is already recorded.
            if (currentDistance > shortestDistances.get(currentNode)) continue;

            // Explore all neighbors of the current node.
            for (DataLoader.Edge edge : graph.get(currentNode)) {
                Integer population = populations.get(edge.destination);
                if (population == null) {
                    population = 1; // Default value if population data is missing.
                }
                // Calculate new distance based on travel time and population priority.
                int populationWeight = population / maxPopulation;
                double newDistance = currentDistance + edge.travelTime - alpha * populationWeight;

                // If this new path is shorter, update and add to the queue.
                if (newDistance < shortestDistances.get(edge.destination)) {
                    shortestDistances.put(edge.destination, (int) newDistance);
                    queue.add(new Main.Pair<>(edge.destination, newDistance));
                }
            }
        }

        return shortestDistances; // Return the map of shortest distances.
    }

    /**
     * Finds an efficient route for a leader to share knowledge, considering shortest path, population, and recency.
     *
     * @param graph The island graph.
     * @param populations A Map with island populations.
     * @param recency A Map tracking the last visit time to each island.
     * @param skills A Map with skill sets of each island.
     * @param homeIsland The starting island for the leader.
     * @return A List representing the sequence of islands in the leader’s route.
     */
    public static List<String> leaderRoutePlan(Map<String, List<DataLoader.Edge>> graph, Map<String, Integer> populations, Map<String, Integer> recency, Map<String, Set<String>> skills, String homeIsland) {
        List<String> route = new ArrayList<>(); // Stores the route sequence.
        Set<String> visited = new HashSet<>();  // Tracks visited islands.
        String currentIsland = homeIsland;      // Start from home island.

        // Continue until all islands have been visited.
        while (visited.size() < populations.size()) {
            visited.add(currentIsland);   // Mark the current island as visited.
            route.add(currentIsland);     // Add it to the route.
            recency.put(currentIsland, recency.getOrDefault(currentIsland, 0) + 1); // Update recency.

            // Get shortest paths from the current island
            Map<String, Integer> distances = shortestPath(graph, populations, currentIsland, 1.5);
            String nextIsland = null;
            double minScore = Double.MAX_VALUE; // Track the island with minimum score.

            // Find the next island to visit based on population and recency priority.
            for (String island : populations.keySet()) {
                if (!visited.contains(island)) {
                    int recencyPriority = recency.getOrDefault(island, 0);
                    double score = distances.get(island) * (1 + recencyPriority / 10.0);
                    if (score < minScore) {
                        minScore = score;
                        nextIsland = island;
                    }
                }
            }

            // If no next island is found, break out of the loop.
            if (nextIsland == null) break;
            currentIsland = nextIsland; // Move to the next island.
        }

        return route;
    }
}
