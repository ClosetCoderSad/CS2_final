*
 * ============================
 *  GRAPHS – QUICK NOTES
 * ============================
 *
 * 1. BASIC GRAPH TERMINOLOGY
 * ---------------------------
 * Graph G = (V, E)
 *   - V (vertices / nodes): elements in the graph
 *   - E (edges): connections between vertices
 *
 * Types:
 *   - Undirected: edge A—B means A connected to B AND B connected to A.
 *   - Directed (digraph): edge A -> B has a direction (A to B only).
 *   - Unweighted: all edges have equal cost (often treated as 1).
 *   - Weighted: edges store a weight (distance, cost, time, etc.).
 *   - Sparse graph: few edges compared to V^2  -> adjacency list preferred.
 *   - Dense graph: many edges, close to V^2    -> adjacency matrix preferred.
 *
 *
 * 2. GRAPH REPRESENTATIONS
 * -------------------------
 * A) Adjacency List
 *    - Idea: for each vertex, store a list of its neighbors.
 *    - Implementations used in this module:
 *
 *      1) Map<String, List<String>> adjList
 *         - Keys: vertex labels (e.g., "A", "B", "C")
 *         - Values: list of neighboring vertex labels.
 *
 *         public class Graph {
 *             private Map<String, List<String>> adjList = new HashMap<>();
 *
 *             // Add a vertex if it does not exist
 *             public void addVertex(String label) {
 *                 adjList.putIfAbsent(label, new ArrayList<>());
 *             }
 *
 *             // Undirected, unweighted edge: connect v1 <-> v2
 *             public void addEdge(String v1, String v2) {
 *                 adjList.get(v1).add(v2);
 *                 adjList.get(v2).add(v1);
 *             }
 *
 *             // Print adjacency list
 *             public void printGraph() {
 *                 for (String v : adjList.keySet()) {
 *                     System.out.println("Vertex " + v + " is connected to: " + adjList.get(v));
 *                 }
 *             }
 *         }
 *
 *      2) Undirected, WEIGHTED adjacency list using an inner Pair class
 *
 *         class WeightedGraph {
 *             static class Pair {
 *                 String vertex;
 *                 int weight;
 *
 *                 public Pair(String vertex, int weight) {
 *                     this.vertex = vertex;
 *                     this.weight = weight;
 *                 }
 *
 *                 @Override
 *                 public String toString() {
 *                     return vertex + " (" + weight + ")";
 *                 }
 *             }
 *
 *             private Map<String, List<Pair>> adjList = new HashMap<>();
 *
 *             public void addVertex(String label) {
 *                 adjList.putIfAbsent(label, new ArrayList<>());
 *             }
 *
 *             // Undirected weighted edge: v1 <-> v2 with weight w
 *             public void addEdge(String v1, String v2, int w) {
 *                 adjList.get(v1).add(new Pair(v2, w));
 *                 adjList.get(v2).add(new Pair(v1, w));
 *             }
 *
 *             // Remove undirected edge between v1 and v2
 *             public void removeEdge(String v1, String v2) {
 *                 List<Pair> list1 = adjList.get(v1);
 *                 List<Pair> list2 = adjList.get(v2);
 *                 if (list1 != null) list1.removeIf(p -> p.vertex.equals(v2));
 *                 if (list2 != null) list2.removeIf(p -> p.vertex.equals(v1));
 *             }
 *         }
 *
 *      3) Adjacency list using ArrayList<ArrayList<Integer>>
 *
 *         class AdjacencyListGraph {
 *             private ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
 *
 *             // Add a new vertex: represented by a new empty list
 *             public void addVertex() {
 *                 adjList.add(new ArrayList<>());
 *             }
 *
 *             // Undirected edge between vertex1 and vertex2
 *             public void addEdge(int v1, int v2) {
 *                 ArrayList<Integer> list1 = getOrCreateList(v1);
 *                 ArrayList<Integer> list2 = getOrCreateList(v2);
 *                 list1.add(v2);
 *                 list2.add(v1);
 *             }
 *
 *             private ArrayList<Integer> getOrCreateList(int index) {
 *                 while (index >= adjList.size()) {
 *                     adjList.add(new ArrayList<>());
 *                 }
 *                 return adjList.get(index);
 *             }
 *         }
 *
 *    - Complexity (Adjacency List)
 *        Space:      O(V + E)
 *        Add edge:   O(1)
 *        Remove edge: O(E) worst-case (scan list)
 *        Edge lookup: O(deg(v)) = O(V) worst-case
 *        Adding vertex: easy (append new list)
 *
 *
 * B) Adjacency Matrix
 *    - Idea: 2D array matrix[i][j] tells whether edge i -> j exists.
 *    - For weighted graphs, entries can store weights instead of boolean.
 *
 *    Undirected, unweighted example:
 *
 *      public class AdjacencyMatrixGraph {
 *          private boolean[][] adjMatrix;
 *          private int numVertices;
 *
 *          public AdjacencyMatrixGraph(int numVertices) {
 *              this.numVertices = numVertices;
 *              adjMatrix = new boolean[numVertices][numVertices];
 *          }
 *
 *          // Add undirected edge between i and j
 *          public void addEdge(int i, int j) {
 *              adjMatrix[i][j] = true;
 *              adjMatrix[j][i] = true;
 *          }
 *
 *          // Remove undirected edge between i and j
 *          public void removeEdge(int i, int j) {
 *              adjMatrix[i][j] = false;
 *              adjMatrix[j][i] = false;
 *          }
 *      }
 *
 *    Directed graph with transpose:
 *
 *      class Transpose {
 *          private boolean[][] adjMatrix;
 *          private int numVertices;
 *
 *          public Transpose(int numVertices) {
 *              this.numVertices = numVertices;
 *              adjMatrix = new boolean[numVertices][numVertices];
 *          }
 *
 *          public void addEdge(int from, int to) {
 *              adjMatrix[from][to] = true;
 *          }
 *
 *          public void removeEdge(int from, int to) {
 *              adjMatrix[from][to] = false;
 *          }
 *
 *          // Build transpose graph: reverse every edge
 *          public Transpose transpose() {
 *              Transpose t = new Transpose(numVertices);
 *              for (int i = 0; i < numVertices; i++) {
 *                  for (int j = 0; j < numVertices; j++) {
 *                      if (adjMatrix[i][j]) {
 *                          t.addEdge(j, i);
 *                      }
 *                  }
 *              }
 *              return t;
 *          }
 *      }
 *
 *    - Complexity (Adjacency Matrix)
 *        Space:        O(V^2)  (independent of number of edges)
 *        Add edge:     O(1)
 *        Remove edge:  O(1)
 *        Edge lookup:  O(1)  (direct index)
 *        Adding vertex: hard (need to resize the matrix)
 *
 *    - Rule of thumb:
 *        * Adjacency LIST: space efficient for sparse graphs; easier vertex ops.
 *        * Adjacency MATRIX: better for dense graphs; O(1) edge lookups.
 *
 *
 * 3. GRAPH TRAVERSAL – DFS & BFS
 * -------------------------------
 * For both, assume a Map<String, List<String>> adjList representation.
 *
 * A) Depth-First Search (DFS)
 *    - Goes as deep as possible along a path, then backtracks.
 *    - Uses RECURSION (implicit stack) or an explicit Stack.
 *    - Good for: connectivity, cycle detection, topological sort (DAG), etc.
 *
 *      public void dfs(String start) {
 *          Set<String> visited = new HashSet<>();
 *          dfs(start, visited);
 *      }
 *
 *      // Recursive helper
 *      private void dfs(String vertex, Set<String> visited) {
 *          if (visited.contains(vertex)) {
 *              return;            // base case
 *          }
 *
 *          visited.add(vertex);
 *          System.out.print(vertex + " ");
 *
 *          List<String> neighbors = adjList.get(vertex);
 *          if (neighbors != null) {
 *              for (String next : neighbors) {
 *                  dfs(next, visited);
 *              }
 *          }
 *      }
 *
 *    - Time complexity:  O(V + E)
 *    - Space complexity: O(V) for visited + recursion stack
 *
 *
 * B) Breadth-First Search (BFS)
 *    - Explores neighbors level by level (layer by layer from the start node).
 *    - Uses a QUEUE.
 *    - Used for: shortest path in unweighted graphs, level-order traversal.
 *
 *      public void bfs(String start) {
 *          Set<String> visited = new HashSet<>();
 *          Queue<String> queue = new LinkedList<>();
 *
 *          queue.add(start);
 *          visited.add(start);
 *
 *          while (!queue.isEmpty()) {
 *              String vertex = queue.poll();
 *              System.out.print(vertex + " ");
 *
 *              List<String> neighbors = adjList.get(vertex);
 *              if (neighbors != null) {
 *                  for (String next : neighbors) {
 *                      if (!visited.contains(next)) {
 *                          visited.add(next);
 *                          queue.add(next);
 *                      }
 *                  }
 *              }
 *          }
 *      }
 *
 *    - Time complexity:  O(V + E)
 *    - Space complexity: O(V)  (visited + queue)
 *
 * Note: DFS and BFS are the same Big-O time & space order; DFS is not
 * inherently "slower" just because of recursion.
 *
 *
 * 4. DIJKSTRA (HIGH-LEVEL NOTE – for later assignments)
 * ------------------------------------------------------
 * - Finds shortest path in a weighted graph with NON-NEGATIVE edge weights.
 * - Commonly implemented with:
 *      * adjacency list + (vertex, distance) min-heap / priority queue
 * - Basic idea:
 *      1. Initialize distances[] with INF, distance[source] = 0.
 *      2. Push (0, source) into a min-priority-queue.
 *      3. While PQ not empty:
 *           - Extract vertex u with smallest distance.
 *           - For each neighbor v of u with edge weight w:
 *                 if dist[u] + w < dist[v]:
 *                     update dist[v]
 *                     push (dist[v], v) into PQ.
 * - Time complexity: O((V + E) log V) using a priority queue.
 *
 *
 * 5. QUICK FILL-IN-THE-BLANK CHEATS (FROM QUIZZES)
 * ------------------------------------------------
 * - Adjacency matrix: representation where adding vertices is harder.
 * - Adjacency list: representation where adding vertices is easier.
 * - Adjacency list: space-efficient for sparse graphs.
 * - Adjacency matrix: space-efficient for dense graphs.
 * - Adjacency matrix: O(1) time for edge operations (add/remove/lookup).
 * - Adjacency matrix: faster for edge lookups.
 * - Number of EDGES does NOT affect adjacency MATRIX space (always V^2). (False statement if claimed.)
 * - DFS and BFS use the same order of memory (O(V)).
 */
