package graph;
/******************************************************************************
 *  Compilation:  javac graph.BreadthFirstSearch.java
 *  Execution:    java graph.BreadthFirstSearch graph.txt s
 *  Dependencies: graph.Graph.java Queue.java Stack.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *
 *  Run (nonrecurisve) breadth-first search on an undirected graph.
 *  Runs in O(E + V) time using O(V) extra space.
 *
 *  Explores the vertices in exactly the same order as graph.breadthFirstSearch.java.
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
 *  % java graph.BreadthFirstSearch tinyG.txt 0
 *  0 1 2 3 4 5 6 
 *
 * % java graph.BreadthFirstSearch tinyG.txt 9
 * 9 10 11 12 
 *
 ******************************************************************************/

import lib.In;

import java.util.Queue;
import java.util.LinkedList;

/**
 *  The {@code graph.BreadthFirstSearch} class represents a data type for finding
 *  the vertices connected to a source vertex <em>s</em> in the undirected
 *  graph.
 *  <p>
 *  This implementation uses a nonrecursive version of breadth-first search
 *  with an explicit stack.
 *  See {@link DepthFirstSearchRecursion} for the classic recursive version.
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
public class BreadthFirstSearch {
    private boolean[] visited;  // marked[v] = is there an s-v path?
    /**
     * Computes the vertices connected to the source vertex {@code s} in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BreadthFirstSearch(Graph G, int s) {
        visited = new boolean[G.V()];
        validateVertex(s);

        // breadth-first search using queue
        Queue<Integer> queue = new LinkedList<>();
        visited[s] = true;
        queue.add(s);
        while (!queue.isEmpty()) {
            int curr = queue.remove();
            for(int w : G.adj(curr)){
                if (!visited[w]) {
                    // discovered vertex w for the first time
                    System.out.println("visit " + w + " from " + curr);
                    visited[w] = true;
                    queue.add(w); //add w to the queue
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

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = visited.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Unit tests the {@code graph.BreadthFirstSearch} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In("mediumG.txt");
        Graph G = new Graph(in);
        int s = 0; //BFS from vertex 0
        BreadthFirstSearch bfs = new BreadthFirstSearch(G, s);
        for (int v = 0; v < G.V(); v++)
            if (bfs.visited(v))
                System.out.print(v + ",");
        System.out.println();
    }
}
