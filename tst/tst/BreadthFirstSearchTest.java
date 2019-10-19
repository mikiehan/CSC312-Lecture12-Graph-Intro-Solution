package tst;

import graph.BreadthFirstSearch;
import graph.Graph;
import lib.In;
import lib.StdOut;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BreadthFirstSearchTest {
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
        BreadthFirstSearch bfs = new BreadthFirstSearch(G, s);
        //Check visited
        for (int v = 0; v < G.V(); v++)
            if (bfs.visited(v))
                System.out.print(v + ","); //list of nodes reachable from 0
        System.out.println();

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
        testAgainstFile("tinyGConn.txt", "expected/BFS_tinyGConn_expected.txt", 0);
    }

    @Test
    public void test2(){
        testAgainstFile("tinyG.txt", "expected/BFS_tinyG_expected.txt", 0);
    }

    @Test
    public void test3(){
        testAgainstFile("mediumG.txt", "expected/BFS_mediumG_expected.txt", 0);
    }
}
