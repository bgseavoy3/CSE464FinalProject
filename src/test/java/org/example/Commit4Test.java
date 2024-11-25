package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.example.Main.parseGraph;

public class Commit4Test {
    @Test
    public void test() throws IOException { // tests output of outputDOTGraph, input file: Commit1Test2.dot
        System.out.println("Start Test 2");
        URL location = getClass().getClassLoader().getResource("Commit1Test2.dot");
        String fileLocation = URLDecoder.decode(location.getPath(), "UTF-8");

        System.out.println("File found");

        MutableGraph g = parseGraph(fileLocation);

        System.out.println("Parse Finished");
        Main.outputDOTGraph(g, "test4Output.dot");
        String expected = Files.readString(Paths.get("test4Output.dot"));
        String real = Files.readString(Paths.get("src/test/resources/Commit1Test3.dot"));
    }
}
