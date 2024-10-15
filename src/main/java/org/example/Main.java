package org.example;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Braden Seavoy\\CSE464\\input.dot";
        //System.out.println(filePath + " - is the filepath for this test");
        try {
            MutableGraph result = parseGraph(filePath);
        } catch (IOException e) {
            System.out.println("IOException error occurred");
        }
    }
    public static MutableGraph parseGraph(String filepath) throws IOException {
        //System.out.println("made it into method");
        MutableGraph result = mutGraph("test").setDirected(true);
        int iterator = 0;
        File file = new File(filepath);
        BufferedReader scanner = new BufferedReader(new FileReader(file));
        String leftSide = null;
        String[] rightSide = null;
        String[] multipleDependence = null;
        boolean canContinue = true;
        boolean canInnerContinue = true;
        System.out.println("made it through init");
        String regExpression = "\\w+|->|[{}\\[\\];]";
        Pattern regPattern = Pattern.compile(regExpression);
        Matcher match;
        String line = scanner.readLine();
        ArrayList<String> words = new ArrayList<>();
        match = regPattern.matcher(line);
        while(match.find())
        {
            words.add(match.group());
        }
        //System.out.println(line + " is the current line");
        //System.out.println("made it through split");
        try
        {
            while(canContinue)
            {
                //System.out.println("Start of outside loop. Iterator is " + iterator);
                if (words.get(iterator).equals("strict"))
                {
                    //System.out.println("strict precursor detected");
                    iterator++;
                    //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                }
                if (words.get(iterator).equals("graph"))
                {
                    //System.out.println("graph detected");
                    iterator++;
                    //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                }
                if (words.get(iterator).equals("digraph"))
                {
                    //System.out.println("digraph detected");
                    iterator++;
                    //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                }
                while (canInnerContinue)
                {
                    TimeUnit.SECONDS.sleep(1);
                    if(iterator == words.size())
                    {
                        //System.out.println("Finished Line, reading new line");
                        line = scanner.readLine();
                        //System.out.println(line + " is the current line");
                        match = regPattern.matcher(line);
                        words = new ArrayList<>();
                        while(match.find())
                        {
                            words.add(match.group());
                        }
                        iterator = 0;
                    }
                    //System.out.println("Start of inside loop after check. Iterator is " + iterator + "and words is " + words.size() + ". Current word is " + words.get(iterator));
                    if (words.get(iterator).equals("{"))
                    {
                        //System.out.println("start of parts of graph");
                        iterator++;
                        //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                    }
                    else if (words.get(iterator).equals("->"))
                    {
                        //System.out.println("middle of dependence statement");
                        iterator++;
                        //System.out.println("iterator is: " + iterator + "and words is " + words.size());

                    }
                    else if (words.get(iterator).equals("["))
                    {
                        //System.out.println("beginning of dependence list");
                        iterator++;
                        //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                        multipleDependence = new String[words.size() - iterator - 2]; // amount of words left inside of [] (-2 because of ];)
                        while (!(words.get(iterator).equals("]"))) {
                            //System.out.println(words.get(iterator).substring(0, words.get(iterator).length() - 1) + " is a part of the dependence list");
                            iterator++;

                        }
                        if (words.get(iterator).equals("]")) {
                            //System.out.println("end of dependence list");
                            iterator++;
                            //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                        } else {
                            //System.out.println("no right bracket detected");
                        }

                    }
                    else if (words.get(iterator).equals(";")) {
                        //System.out.println("end of statement");
                        line = scanner.readLine();
                        match = regPattern.matcher(line);
                        words = new ArrayList<>();
                        while(match.find())
                        {
                            words.add(match.group());
                        }
                        iterator = 0;
                    }
                    else if (words.get(iterator).equals("}"))
                    {
                        //System.out.println("end of declaration of graph");
                        canInnerContinue = false;
                    }
                    else {
                        if (leftSide == null) // the word at hand is the left side of the statement
                        {
                            leftSide = words.get(iterator);
                            iterator++;
                        } else // the word at hand is the right side of the statement
                        {
                            if (multipleDependence == null) // single dependence
                            {
                                rightSide = new String[1];
                                rightSide[0] = words.get(iterator);
                            } else {
                                rightSide = new String[multipleDependence.length];
                                System.arraycopy(multipleDependence, 0, rightSide, 0, multipleDependence.length);
                            }
                            iterator++;
                            for(int i = 0; i < rightSide.length; i++)
                            {
                                MutableNode left = mutNode(leftSide);
                                MutableNode right = mutNode(rightSide[i]);
                                result.add(left.addLink(right));
                            }
                            leftSide = null;
                            rightSide = null;
                            multipleDependence = null;
                        }
                    }
                }
                line = scanner.readLine();
//
                canContinue = false;
            }
        }
        catch(IOException e)
        {
            System.out.println("error with file parsing");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void toString(MutableGraph g)
    {
        int nodeCount = 0;
        int edgeCount = 0;
        String output = "Nodes List\n__________";
        for(MutableNode node : g.nodes())
        {
            nodeCount++;
            output += "\nNode: " + node.name().toString() + "\n";
            output += "Edges List for node " + node.name().toString() + "\n";
            for(Link link : node.links())
            {
                edgeCount++;
                output += "Edge from: " + node.name().toString() + " to " + link.to().name().toString() + "\n";
            }
        }
        output += "Number of Nodes: " + nodeCount + "\n" + "Number of Edges: " + edgeCount;
        System.out.println(output);
    }
    public static void outputGraph(MutableGraph g, String filepath) throws IOException {
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(filepath));
    }

}