package org.example;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static org.example.Main.parseGraph;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Commit1Test {

    @Test
    public void firstTest() throws IOException { // test base functionality, input is Commit1Test1.dot
        System.out.println("Start Test 1");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test1.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        MutableGraph g = parseGraph(fileLocation);
        MutableNode left = mutNode("A");
        MutableNode right = mutNode("B");

        Boolean leftCheck = g.nodes().stream().anyMatch(node -> node.name().equals(left.name()));
        Boolean rightCheck = g.nodes().stream().anyMatch(node -> node.name().equals(right.name()));

        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("output1.png"));
        String output = Graphviz.fromGraph(g).render(Format.PNG).toString();
        assertTrue(leftCheck);
        assertTrue(rightCheck);
        assertFalse(output.isEmpty());
    }
    @Test
    public void secondTest() throws IOException { // test example given in assignment description, input is Commit1Test2.dot
        System.out.println("Start Test 2");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        MutableNode A = mutNode("A");
        MutableNode B = mutNode("B");
        MutableNode C = mutNode("C");
        MutableNode D = mutNode("D");
        MutableNode E = mutNode("E");
        MutableNode F = mutNode("F");
        MutableNode G = mutNode("G");
        MutableNode H = mutNode("H");

        MutableGraph actual = mutGraph("test").setDirected(true);
        actual.add(A);
        actual.add(B);
        actual.add(C);
        actual.add(D);
        actual.add(E);
        actual.add(F);
        actual.add(G);
        actual.add(H);

        Boolean ACheck = g.nodes().stream().anyMatch(node -> node.name().equals(A.name()));
        Boolean BCheck = g.nodes().stream().anyMatch(node -> node.name().equals(B.name()));
        Boolean CCheck = g.nodes().stream().anyMatch(node -> node.name().equals(C.name()));
        Boolean DCheck = g.nodes().stream().anyMatch(node -> node.name().equals(D.name()));
        Boolean ECheck = g.nodes().stream().anyMatch(node -> node.name().equals(E.name()));
        Boolean FCheck = g.nodes().stream().anyMatch(node -> node.name().equals(F.name()));
        Boolean GCheck = g.nodes().stream().anyMatch(node -> node.name().equals(G.name()));
        Boolean HCheck = g.nodes().stream().anyMatch(node -> node.name().equals(H.name()));
        Boolean Link1Check = linkCheck(g, "A", "B");
        Boolean Link2Check = linkCheck(g, "B", "C");
        Boolean Link3Check = linkCheck(g, "C", "D");
        Boolean Link4Check = linkCheck(g, "D", "A");
        Boolean Link5Check = linkCheck(g, "A", "E");
        Boolean Link6Check = linkCheck(g, "E", "F");
        Boolean Link7Check = linkCheck(g, "E", "G");
        Boolean Link8Check = linkCheck(g, "F", "H");
        Boolean Link9Check = linkCheck(g, "G", "H");


        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("outputTest2.png"));

        assertTrue(ACheck);
        assertTrue(BCheck);
        assertTrue(CCheck);
        assertTrue(DCheck);
        assertTrue(ECheck);
        assertTrue(FCheck);
        assertTrue(GCheck);
        assertTrue(HCheck);
        assertTrue(Link1Check);
        assertTrue(Link2Check);
        assertTrue(Link3Check);
        assertTrue(Link4Check);
        assertTrue(Link5Check);
        assertTrue(Link6Check);
        assertTrue(Link7Check);
        assertTrue(Link8Check);
        assertTrue(Link9Check);


    }
    @Test
    public void thirdTest() throws IOException { // test bracket link setup, input is Commit1Test4.dot

        System.out.println("Start Test 3");
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test3.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");

        MutableNode A = mutNode("A");
        MutableNode B = mutNode("B");
        MutableNode C = mutNode("C");
        MutableNode D = mutNode("D");

        Boolean ACheck = g.nodes().stream().anyMatch(node -> node.name().equals(A.name()));
        Boolean BCheck = g.nodes().stream().anyMatch(node -> node.name().equals(B.name()));
        Boolean CCheck = g.nodes().stream().anyMatch(node -> node.name().equals(C.name()));
        Boolean DCheck = g.nodes().stream().anyMatch(node -> node.name().equals(D.name()));

        Boolean Link1Check = linkCheck(g, "A", "B");
        Boolean Link2Check = linkCheck(g, "A", "C");
        Boolean Link3Check = linkCheck(g, "A", "D");

        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("outputTest4.png"));


        assertTrue(ACheck);
        assertTrue(BCheck);
        assertTrue(CCheck);
        assertTrue(DCheck);
        assertTrue(Link1Check);
        assertTrue(Link2Check);
        assertTrue(Link3Check);
    }
    public boolean linkCheck(MutableGraph g, String left, String right)
    {
        for (MutableNode node : g.nodes())
        {
            for(Link link : node.links())
            {
                if(node.name().toString().equals(left) && link.to().name().toString().equals(right))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
