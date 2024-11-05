import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

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

    /**
     * Distributes an island's natural resource to all other islands.
     *
     * @param graph The island graph.
     * @param resources A Map with the type and quantity of an island's natural resource.
     * @param source The starting island whose resource will be distributed.
     * @param canoes The number of canoes that will distribute the resource.
     * @return A list of strings detailing the route taken by the canoes, including return trips.
     */
    public static List<String> distributeNaturalResource(Map<String, List<DataLoader.Edge>> graph, Map<String, Map<String, Integer>> resources, String source, int canoes) {
        
        // Initializes data structures
        PriorityQueue<IslandState> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.time));   // Queue where shortest travel time is prioritized
        Map<String, Integer> shortestPaths = new HashMap<>();                                           // Store shortest path time to each island
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Integer> resourceDistribution = new HashMap<>();                                                 // Track visited islands
        List<String> route = new ArrayList<>();                                                         // Store the route sequence.
        
        // Initializes variables
        int totalTime = 0;              // Total time taken to distribute the resource
        int remainingCanoes = canoes;   // Number of canoes remaining before returning to the source
        
        for (String island : graph.keySet()) { 
           shortestPaths.put(island, Integer.MAX_VALUE); 
           visited.put(island, false); 
           resourceDistribution.put(island, 0); 
        }
        
        // Adds source island to queue and sets shortest path time to 0
        queue.add(new IslandState(source, 0));
        shortestPaths.put(source, 0);

        // Continues distributing resource until all islands are visited
        while (!queue.isEmpty()) {
            // Retrieves next island to visit with the shortest travel time
            IslandState current = queue.poll();
            String currentIsland = current.island;

            // Skips the island if 'true' is returned, since it has already been visited
            if (visited.getOrDefault(currentIsland, false)) {
                continue;
            }

            // Visits the island, updates the route and total time
            visited.put(currentIsland, true);
            route.add("Visited " + currentIsland);
            totalTime += current.time;
            

            // Decrements the number of stocked canoes on each visit to an island
            remainingCanoes--;
            // Returns to the source island to reload canoes if all canoes are used up
            if (remainingCanoes == 0) {
                route.add("Returning to " + source + " to reload");
                totalTime += shortestPaths.get(currentIsland);                  // Adds return time to total time   
                queue.add(new IslandState(source, shortestPaths.get(source)));  // Adds source island back to queue
                remainingCanoes = canoes;                                       // Resets the number of canoes
                continue;                                                       // Begins distributing resource again
            }

            // Processes each island directly connected to the current island
            for (DataLoader.Edge edge : graph.getOrDefault(currentIsland, new ArrayList<>())) {
                // Calculates cumulative travel time to reach the connected island, adding the time it takes to get from
                // the current island to the connected island and the time it takes to get from the source to the connected island
                int newTime = current.time + edge.travelTime;
                
                // Checks if the new time is shorter than the current shortest path time to the connected island
                if (newTime < shortestPaths.getOrDefault(edge.destination, Integer.MAX_VALUE)) {
                    shortestPaths.put(edge.destination, newTime);               // Updates the shortest path time to the connected island
                    queue.add(new IslandState(edge.destination, newTime));      // Adds the connected island to the queue with the new time
                }
            }
        }

        // Outputs total time for distribution and returns the route taken
        System.out.println("Total path timelength: " + totalTime);
        return route;

    }
   // Method to distribute a resource from the start island to other islands 
   public static List<String> distributeResource(Map<String, List<DataLoader.Edge>> graph, Map<String, Map<String, Integer>> resources, String startIsland, String resource, int numCanoes, int canoeCapacity) { 
   PriorityQueue<DataLoader.Edge> queue = new PriorityQueue<>(Comparator.comparingInt(e -> e.travelTime)); 
   Map<String, Integer> distances = new HashMap<>(); 
   Map<String, Boolean> visited = new HashMap<>(); 
   Map<String, Integer> resourceDistribution = new HashMap<>(); 
   List<String> distributionRoute = new ArrayList<>(); 
   
   // Initialize distances and visited maps 
   for(String island : graph.keySet()) { 
      distances.put(island, Integer.MAX_VALUE); visited.put(island, false); 
      resourceDistribution.put(island, 0); 
   } 
   
   // Set the distance to the start island to 0 and add it to the queue 
   distances.put(startIsland, 0); 
   queue.add(new DataLoader.Edge(startIsland, 0)); 
   
   // Dijkstra's algorithm to find the shortest paths from the start island 
   while (!queue.isEmpty()) { 
      DataLoader.Edge current = queue.poll(); 
      String currentIsland = current.destination; 
      
      if (visited.get(currentIsland)) continue; 
         visited.put(currentIsland, true); 
         
         for (DataLoader.Edge edge : graph.get(currentIsland)) { 
            String neighbor = edge.destination; 
            int newDist = distances.get(currentIsland) + edge.travelTime; 
            
            if (newDist < distances.get(neighbor)) { 
               distances.put(neighbor, newDist); 
               queue.add(new DataLoader.Edge(neighbor, newDist)); 
            } 
         } 
      } 
      
      // Check if the resource is available on the start island 
      if (!resources.containsKey(startIsland) || !resources.get(startIsland).containsKey(resource)) { 
         System.out.println("\nResource not found on the start island."); 
         return distributionRoute; 
      } 
      
      // Distribute the resource to other islands 
      int remainingResource = resources.get(startIsland).get(resource); 
      
      for (String island : distances.keySet()) { 
         
         if (!island.equals(startIsland) && remainingResource > 0) { 
            int neededResource = Math.min(canoeCapacity, remainingResource); 
            resourceDistribution.put(island, neededResource); 
            remainingResource -= neededResource; 
            distributionRoute.add("Deliver " + neededResource + " units of " + resource + " to " + island); 
         } 
      } 
      return distributionRoute; 
   }
    
    // Helper class stores the state of the island with name and travel time
    private static class IslandState {
        String island;
        int time;

        IslandState(String island, int time) {
            this.island = island;
            this.time = time;
        }
    }
}
