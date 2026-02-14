package ee.taltech.algoritmid.friends;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

public class P11 {
    public DirectedGraph graph = new DirectedGraph();

    private class DirectedGraph {
        private Map<String, List<String>> graph = new HashMap<>();

        /**
         * Add undirected edge to the graph.
         *
         * @param one   one element of the edge
         * @param other the other element of edge
         */
        public void addEdge(String one, String other) {
            if (!graph.containsKey(one)) {
                List<String> edges = new ArrayList<>();
                edges.add(other);
                graph.put(one, edges);
            } else {
                if (!graph.get(one).contains(other)) {
                    graph.get(one).add(other);
                }
            }
        }

        /**
         * Return the graph.
         *
         * @return the internal graph structure.
         */
        public Map<String, List<String>> getGraph() {
            return graph;
        }

        /**
         * Perform breadth first search.
         *
         * @param start the vertex to start the search from
         * @param goal  the goal vertex to find
         * @return the number of vertices of the path from start to goal including start and goal (e.g.,
         * start = A, goal = C, path = A, B, C => 3) and the path itself as a list of integers.
         * NB! You can return null as path and only return the number of nodes
         * that connect the start and goal vertices for partial credit
         * (some tests only check for number of nodes)
         */
        public SimpleEntry<Integer, List<String>> breadthFirstSearch(String start, String goal) {
            Map<String, Boolean> visited = new HashMap<>();
            Map<String, Integer> distance = new HashMap<>();
            Map<String, String> parent = new HashMap<>();

            for (String i : graph.keySet()) {
                visited.put(i, false);
                distance.put(i, Integer.MAX_VALUE);
                parent.put(i, null);
            }
            visited.putIfAbsent(start, false);
            visited.putIfAbsent(goal, false);
            distance.putIfAbsent(start, 0);
            parent.putIfAbsent(start, null);

            visited.put(start, true);
            distance.put(start, 0);
            parent.put(start, null);

            Queue<String> queue = new LinkedList<>();
            queue.add(start);

            while (!queue.isEmpty()) {
                String u = queue.poll();
                if (u.equals(goal)) break;

                List<String> neighbors = graph.get(u);
                if (neighbors == null) continue;

                for (String v : neighbors) {
                    if (!visited.containsKey(v)) {
                        visited.put(v, false);
                        distance.put(v, Integer.MAX_VALUE);
                        parent.put(v, null);
                    }

                    if (!visited.get(v)) {
                        visited.put(v, true);
                        parent.put(v, u);
                        distance.put(v, distance.get(u) + 1);
                        queue.add(v);
                    }
                }
            }

            if (!visited.containsKey(goal) || !visited.get(goal)) {
                return null;
            }

            List<String> path = new ArrayList<>();
            String current = goal;
            while (current != null) {
                path.add(current);
                current = parent.get(current);
            }
            Collections.reverse(path);

            return new SimpleEntry<>(path.size(), path);
        }
    }


    /**
     * Use buildGraphAndFindLink to build a graph using the DirectedGraph class and then use its breadthFirstSearch to
     * return the links.
     *
     * @param friends the list of friends as pairs
     *                (e.g., [["Juhan", "Jaan"], ["Juhan", "Siiri"]] means that "Juhan" knows "Jaan" and "Siiri")
     * @param pair    the pair to be searched
     * @return the number of people that connect the searched pair including the pair itself (e.g., if pair is
     * = ["Mark", "Johanna"] and path is ["Mark", "Peter", "Siiri", "Johanna"], the number of people is 4) the list of people that connect
     * the searched pair (e.g., pair = ["Mark", "Sam"] => result = ["Mark", "Siiri", "Helen", "Peeter", "Sam"])
     */
    public SimpleEntry<Integer, List<String>> buildGraphAndFindLink(List<SimpleEntry<String, String>> friends,
                                                                    SimpleEntry<String, String> pair) {
        graph = new DirectedGraph();

        for (SimpleEntry<String, String> entry : friends) {
            String one = entry.getKey();
            String other = entry.getValue();
            graph.addEdge(one, other);
        }

        if (!graph.getGraph().containsKey(pair.getKey()) &&
                !graph.getGraph().containsKey(pair.getValue())) {
            return new SimpleEntry<>(0, null);
        }

        return graph.breadthFirstSearch(pair.getKey(), pair.getValue());
    }
}