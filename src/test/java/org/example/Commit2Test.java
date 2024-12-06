package org.example;

import guru.nidi.graphviz.model.MutableGraph;

import java.io.*;

import org.junit.jupiter.api.Test;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static org.example.Main.parseGraph;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Commit2Test {
    @Test
    public void firstTest() throws IOException //tests basic functionality by adding 1 node. input file: Commit1Test1.dot
    {

        MutableGraph g = parseGraph("Commit1Test1.dot");

        g = Main.addNode(g, "C");

        boolean CCheck = g.nodes().stream().anyMatch(node -> node.name().toString().equals("C"));
        Main.toString(g);
        assertTrue(CCheck);
    }

    @Test
    public void secondTest() throws IOException //tests additional functionality by trying to add node that is already added. Input file: Commit1Test1
    {

        MutableGraph g = parseGraph("Commit1Test1.dot");
        String input = Main.toString(g);
        String nodeName = "B";

        g = Main.addNode(g, nodeName);
        String[] nodeList = {"A", "B"};
        String[] edgeList = {"A -> B"};

        Boolean doubleCheck = Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
        assertTrue(doubleCheck);
    }
    @Test
    public void thirdTest() throws IOException //tests basic functionality of addNodes by adding multiple node. input file: Commit1Test1.dot
    {

        MutableGraph g = parseGraph("Commit1Test1.dot");
        String[] addNodes = {"C", "D", "E", "F"};
        g = Main.addNodes(g, addNodes);

        Boolean CCheck = g.nodes().stream().anyMatch(node -> node.name().toString().equals("C"));
        Boolean DCheck = g.nodes().stream().anyMatch(node -> node.name().toString().equals("D"));
        Boolean ECheck = g.nodes().stream().anyMatch(node -> node.name().toString().equals("E"));
        Boolean FCheck = g.nodes().stream().anyMatch(node -> node.name().toString().equals("F"));

        assertTrue(CCheck);
        assertTrue(DCheck);
        assertTrue(ECheck);
        assertTrue(FCheck);
    }
    @Test
    public void fourthTest() throws IOException //tests additional functionality by trying to add node that is already added. Input file: Commit1Test1
    {

        MutableGraph g = parseGraph("Commit1Test1.dot");
        String input = Main.toString(g);
        String[] nodeNames = {"B", "C", "D", "E", "F"};

        g = Main.addNodes(g, nodeNames);
        String[] nodeList = {"A", "B", "C", "D", "E", "F"};
        String[] edgeList = {"A -> B"};

        Main.toString(g);
        Commit1Test2.checkToStringInfo(input, nodeList, edgeList);
    }

}
