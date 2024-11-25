package org.example;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static org.example.Main.parseGraph;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Commit1Test2 {

    @Test
    public void firstTest() throws IOException { // tests basic use of toString. Input file: Commit1Test1.dot
        System.out.println("Start Test 1");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test1.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        String input = Main.toString(g);
        String[] nodes = {"A", "B"};
        String[] edges = {"A -> B"};
        assertTrue(checkToStringInfo(input, nodes, edges));
    }

    @Test
    public void secondTest() throws IOException // tests extensive use of toString with more edges than nodes, input file: Commit1Test2.dot
    {
        System.out.println("Start Test 2");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        String input = Main.toString(g);
        String[] nodes = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[] edges = {"A -> B", "B -> C", "C -> D", "D -> A", "A -> E", "E -> F", "E -> G", "F -> H", "G -> H"};
        assertTrue(checkToStringInfo(input, nodes, edges));

    }

    @Test
    public void thirdTest() throws IOException { // tests toString when using different node setups. Input file: Commit1Test3.dot
        System.out.println("Start Test 3");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test3.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        String input = Main.toString(g);
        String[] nodes = {"A", "B", "C", "D"};
        String[] edges = {"A -> B", "A -> C", "A -> D"};
        assertTrue(checkToStringInfo(input, nodes, edges));
    }

    public static boolean checkToStringInfo(String input, String[] nodeList, String[] edgeList) throws IOException
    {
        ArrayList<String> graphNodeList = new ArrayList<String>();
        ArrayList<String> graphEdgeList = new ArrayList<String>();
        String numOfNodes = "";
        String numOfEdges = "";
        BufferedReader scanner = new BufferedReader(new StringReader(input));
        String line = "";
        while ((line = scanner.readLine()) != null) {
            if (line.contains("Node:")) {
                graphNodeList.add(line.substring(line.indexOf(" ") + 1));
                //System.out.println(line.substring(line.indexOf(" ") + 1));
            }
            if (line.contains("Edge from:")) {
                graphEdgeList.add(line.substring(line.indexOf(": ") + 2, line.indexOf("to") - 1) + " -> " + line.substring(line.indexOf("to") + 3));
                //System.out.println(line.substring(line.indexOf(": ") + 2, line.indexOf("to") - 1) + " -> " + line.substring(line.indexOf("to") + 3) + ".");
            }
            if (line.contains("Number of Nodes:")) {
                numOfNodes = (line.substring(line.indexOf(": ") + 2));
                //System.out.println("Number of Nodes found: " + numOfNodes);
            }
            if (line.contains("Number of Edges:")) {
                numOfEdges = (line.substring(line.indexOf(": ") + 2));
                //System.out.println("Number of Edges found: " + numOfEdges);
            }
        }

        if (Integer.parseInt(numOfNodes) == nodeList.length) {
            //System.out.println("Correct number of nodes");
        } else {
            //System.out.println("Incorrect number of nodes");
            return false;
        }
        if (Integer.parseInt(numOfEdges) == edgeList.length) {
            //System.out.println("Correct number of edges");
        } else {
            //System.out.println("Incorrect number of edges");
            return false;
        }

        Boolean[] nodeFound = new Boolean[nodeList.length];
        for(int i = 0; i < nodeFound.length; i++)
        {
            nodeFound[i] = false;
        }
        Boolean[] edgeFound = new Boolean[edgeList.length];
        for(int i = 0; i < edgeFound.length; i++)
        {
            edgeFound[i] = false;
        }

        for (int i = 0; i < nodeList.length; i++) {
            for (int j = 0; j < graphNodeList.size(); j++) {
                if (nodeList[i].equals(graphNodeList.get(j))) {
                    nodeFound[j] = true;
                }
            }
        }
        for (int i = 0; i < edgeList.length; i++) {
            for (int j = 0; j < graphEdgeList.size(); j++) {
                if (edgeList[i].equals(graphEdgeList.get(j))) {
                    edgeFound[j] = true;
                }
            }
        }
        for (int i = 0; i < nodeFound.length; i++) {
            if (nodeFound[i] == false) {
                return false;
            }
        }
        for (int i = 0; i < edgeFound.length; i++) {
            if (edgeFound[i] == false) {
                return false;
            }
        }
        return true;
    }
}
