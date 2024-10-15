package org.example;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static org.example.Main.parseGraph;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Commit1Test {

    @Test
    public void firstTest() throws IOException {
        java.net.URL location = getClass().getClassLoader().getResource("Commit1Test1.dot");
        String fileLocation = java.net.URLDecoder.decode(location.getPath(), "UTF-8");
        System.out.println(fileLocation);
        MutableGraph g = parseGraph(fileLocation);
        System.out.println("parseGraph is finished");
        MutableNode left = mutNode("A");
        MutableNode right = mutNode("B");
        Boolean leftCheck = g.nodes().stream().anyMatch(node -> node.name().equals(left.name()));
        Boolean rightCheck = g.nodes().stream().anyMatch(node -> node.name().equals(right.name()));
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File("output.png"));
        String output = Graphviz.fromGraph(g).render(Format.PNG).toString();
        assertTrue(leftCheck);
        assertTrue(rightCheck);
        assertFalse(output.isEmpty());
        Main.toString(g);
        Main.
    }
}
