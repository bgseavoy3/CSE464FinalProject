package org.example;

import guru.nidi.graphviz.model.MutableGraph;

import java.io.*;

import org.junit.jupiter.api.Test;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static org.example.Main.parseGraph;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Commit3Test {
    @Test
    public void firstTest() throws IOException { // add an edge between two existing nodes. Input File: Commit1Test3.dot
        System.out.println("Start Test 3");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        g = Main.addEdge(g, "B", "C");

        String input = Main.toString(g);
        String[] nodeList = {"A", "B", "C", "D"};
        String[] edgeList = {"A -> B", "A -> C", "A -> D", "B -> C"};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);

    }
    @Test
    public void secondTest() throws IOException { // add an edge between two new nodes. Input File: Commit1Test3.dot
        System.out.println("Start Test 3");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test3.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        g = Main.addEdge(g, "B", "G");

        String input = Main.toString(g);
        String[] nodeList = {"A", "B", "C", "D", "G"};
        String[] edgeList = {"A -> B", "A -> C", "A -> D", "B -> G"};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }
    @Test
    public void thirdTest() throws IOException { // add an edge between a new edge and an old edge. Input File: Commit1Test3.dot
        System.out.println("Start Test 3");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test3.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        g = Main.addEdge(g, "G", "H");

        String input = Main.toString(g);
        String[] nodeList = {"A", "B", "C", "G", "H"};
        String[] edgeList = {"A -> B", "A -> C", "A -> D", "G -> H"};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }
}
