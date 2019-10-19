package graph;
/******************************************************************************
 *  Compilation:  javac graph.NonrecursiveDFS.java
 *  Execution:    java graph.NonrecursiveDFS graph.txt s
 *  Dependencies: graph.Graph.java Queue.java Stack.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *
 *  Run nonrecurisve depth-first search on an undirected graph.
 *  Runs in O(E + V) time using O(V) extra space.
 *
 *  Explores the vertices in exactly the same order as graph.DepthFirstSearch.java.
 *
 *  %  java graph.Graph tinyG.txt
 *  13 vertices, 13 edges 
 *  0: 6 2 1 5 
 *  1: 0 
 *  2: 0 
 *  3: 5 4 
 *  4: 5 6 3 
 *  5: 3 4 0 
 *  6: 0 4 
 *  7: 8 
 *  8: 7 
 *  9: 11 10 12 
 *  10: 9 
 *  11: 9 12 
 *  12: 11 9 
 *
 *  % java graph.NonrecursiveDFS tinyG.txt 0
 *  0 1 2 3 4 5 6 
 *
 * % java graph.NonrecursiveDFS tinyG.txt 9
 * 9 10 11 12 
 *
 ******************************************************************************/

import lib.In;

import java.util.Stack;

/**
 *  The {@code graph.NonrecursiveDFS} class represents a data type for finding
 *  the vertices connected to a source vertex <em>s</em> in the undirected
 *  graph.
 *  <p>
 *  This implementation uses a nonrecursive version of depth-first search
 *  with an explicit stack.
 *  See {@link DepthFirstPathsRecursion} for the classic recursive version.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DepthFirstPathsNoRecursion {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] visited;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    /**
     * Computes the vertices connected to the source vertex {@code s} in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstPathsNoRecursion(Graph G, int s) {
        visited = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        validateVertex(s);
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        distTo[s] = 0;

        // depth-first search using an explicit stack
        Stack<Integer> stack = new Stack<>();
        visited[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            int curr = stack.pop();
            for(int w : G.adj(curr)){
                if (!visited[w]) {
                    // discovered vertex w for the first time
                    System.out.println("visit " + w + " from " + curr);
                    visited[w] = true;
                    edgeTo[w] = curr; //visited w from curr
                    distTo[w] = distTo[curr] + 1;
                    stack.push(w);
                }
            }
        }
    }

    /**
     * Is vertex {@code v} connected to the source vertex {@code s}?
     * @param v the vertex
     * @return {@code true} if vertex {@code v} is connected to the source vertex {@code s},
     *    and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean visited(int v) {
        validateVertex(v);
        return visited[v];
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     *
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     *
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return visited[v];
    }

    /**
     * Returns a shortest path between the source vertex {@code s} (or sources)
     * and {@code v}, or {@code null} if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = visited.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Unit tests the {@code graph.NonrecursiveDFS} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In("tinyCG.txt");
        Graph G = new Graph(in);
        int s = 0;
        DepthFirstPathsNoRecursion dfs = new DepthFirstPathsNoRecursion(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                System.out.print(s + " to " + v + " (" + dfs.distTo(v) + "): ");
                Stack<Integer> path = (Stack<Integer>) dfs.pathTo(v);
                while (!path.isEmpty()) {
                    int x = path.pop();
                    if (x == s) System.out.print(x);
                    else System.out.print("-" + x);
                }
                System.out.println();
            } else {
                System.out.println(s + " to " + v + " (-): not connected");
            }
        }
    }
}
