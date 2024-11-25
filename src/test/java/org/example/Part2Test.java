package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.example.Main.parseGraph;

public class Part2Test {
    @Test
    public void testOne() throws IOException // tests basic ability to remove node. Uses Commit1Test3.dot
    {
        System.out.println("Start Test 1");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        g = Main.removeNode(g, "B");

        String input = Main.toString(g);
        String[] nodeList = {"A", "C", "D"};
        String[] edgeList = {"A -> C", "A -> D"};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }
    @Test
    public void testTwo() throws IOException // test ability to remove multiple nodes. Uses Commit1Test3.dot
    {
        System.out.println("Start Test 2");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        String[] array = {"B", "C", "D"};

        g = Main.removeNodes(g, array);

        String input = Main.toString(g);
        String[] nodeList = {"A"};
        String[] edgeList = {};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }
    @Test
    public void testThree() throws IOException // test ability to handle case where node that is being removed is not a real node. Uses Commit1Test3.dot
    {
        System.out.println("Start Test 3");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        g = Main.removeNode(g, "X");

        String input = Main.toString(g);
        String[] nodeList = {"A","B","C","D"};
        String[] edgeList = {"A -> B", "A -> C", "A -> D"};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }
    @Test
    public void testFour() throws IOException // test ability to handle when all 3 nodes being removed are not real. Uses Commit1Test3.dot
    {
        System.out.println("Start Test 4");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        String[] array = {"X", "Y", "Z"};

        g = Main.removeNodes(g, array);

        String input = Main.toString(g);
        String[] nodeList = {"A","B","C","D"};
        String[] edgeList = {"A -> B", "A -> C", "A -> D"};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }
    @Test
    public void testFive() throws IOException // test ability to handle when all nodes that are being removed are not real nodes. Uses Commit1Test3.dot
    {
        System.out.println("Start Test 5");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        String[] array = {"A", "B", "Z"};

        g = Main.removeNodes(g, array);

        String input = Main.toString(g);
        String[] nodeList = {"C","D"};
        String[] edgeList = {};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }
    @Test
    public void testSix() throws IOException // test ability to remove an edge. Uses Commit1Test3.dot
    {
        System.out.println("Start Test 6");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        g = Main.removeEdge(g, "A", "B");

        String input = Main.toString(g);
        String[] nodeList = {"A","B","C","D"};
        String[] edgeList = {"A -> C", "A -> D"};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }
    @Test
    public void testSeven() throws IOException // test ability to handle a case where an edge does not exist Uses Commit1Test3.dot
    {
        System.out.println("Start Test 7");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        g = Main.removeEdge(g, "B", "C");

        String input = Main.toString(g);
        String[] nodeList = {"A","B","C","D"};
        String[] edgeList = {"A -> B","A -> C", "A -> D"};

        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }

}
