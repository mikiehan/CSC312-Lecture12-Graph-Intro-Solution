package tst;

import graph.BreadthFirstPaths;
import graph.BreadthFirstSearch;
import graph.Graph;
import lib.In;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class BreadthFirstPathsTest {
    private static String dumpFileContentsToString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            Assert.fail("Could not load file: " + filePath);
            return null;
        }
    }

    private static String captureOutput(Graph G, int s) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        //Call bfs
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                System.out.print(s + " to " + v + " (" + bfs.distTo(v) + "): ");
                Stack<Integer> path = (Stack<Integer>) bfs.pathTo(v);
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

        System.out.flush();
        System.setOut(oldOut);
        return baos.toString();
    }

    private void testAgainstFile(String inputFile, String expectedFile, int s){
        In in = new In(inputFile);
        Graph G = new Graph(in);
        String actual = captureOutput(G, s);
        String expected = dumpFileContentsToString(expectedFile);
        Assert.assertEquals("Output must match expected value", expected, actual);
    }

    @Test
    public void test1(){
        testAgainstFile("tinyCG.txt", "expected/BFP_tinyCG_expected.txt", 0);
    }

    @Test
    public void test2(){
        testAgainstFile("mediumG.txt", "expected/BFP_mediumG_expected.txt", 0);
    }
}
