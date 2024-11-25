package org.example;
import java.io.*;
import java.util.*;
import java.util.regex.*;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class Main {
    public enum type {
        BFS,
        DFS
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the file path of your dot file");
        String filePath = sc.nextLine();
        MutableGraph result = parseGraph(filePath);
        System.out.println("File is parsed successfully");
        boolean exit = false;
        String input;
        while (!exit) {
            System.out.println("Please enter the number for what would would like to do\n 1: print out graph info\n2: add a new node\n3: add a new edge\n4: output graph to a file location\n5: exit");
            input = sc.nextLine();
            if (input == "1") {
                Main.toString(result);
            }
            if (input == "2") {
                System.out.println("Type 1 for 1 entry, 2 for multiple");
                input = sc.nextLine();
                if (input == "1") {
                    System.out.println("Enter new node name");
                    input = sc.nextLine();
                    Main.addNode(result, input);
                } else {
                    System.out.println("Enter new node names and press enter after each to submit, enter \"exit\" to stop entering names");
                    while (!(input.equals("exit"))) {
                        input = sc.nextLine();
                        if (input.equals("exit")) {

                        } else {
                            Main.addNode(result, input);
                        }
                    }
                }
            }
            if (input == "3") {
                System.out.println("Enter source node name");
                String input1 = sc.nextLine();
                System.out.println("Enter destination node name");
                String input2 = sc.nextLine();
                Main.addEdge(result, input1, input2);
            }
            if (input == "4") {
                System.out.println("Enter file output location");
                String input1 = sc.nextLine();
                System.out.println("Enter output type. Current forms available are dot and png");
                String input2 = sc.nextLine();
                if (input2.equals("dot")) {
                    Main.outputGraph(result, input1);
                } else {
                    Main.outputGraphics(result, input1, input2);
                }
            }
            if (input == "5") {
                exit = true;
            }
        }
    }

    public static MutableGraph parseGraph(String filepath) throws IOException {
        //System.out.println("made it into method");
        MutableGraph result = mutGraph("test").setDirected(true);
        int iterator = 0;
        File file = new File(filepath);
        BufferedReader scanner = new BufferedReader(new FileReader(file));
        String leftSide = null;
        String rightSide = null;
        boolean canContinue = true;
        boolean canInnerContinue = true;
        boolean inBracket = false;
        //System.out.println("made it through init");
        String regExpression = "\\w+|->|[{}\\[\\];,]";
        Pattern regPattern = Pattern.compile(regExpression);
        Matcher match;
        String line = "";
        int current;
        char c;

        while ((line = scanner.readLine()) != null) {
            if (line.indexOf(';') != -1 || line.indexOf('}') != -1) {
                break;
            }
        }
        //System.out.println("line is: " + line);
        ArrayList<String> words = new ArrayList<>();
        match = regPattern.matcher(line);
        while (match.find()) {
            words.add(match.group());
        }
        //System.out.println(line + " is the current line");
        //System.out.println("made it through split");
        try {
            while (canContinue) {
                //System.out.println("Start of outside loop. Iterator is " + iterator);
                if (words.get(iterator).equals("strict")) {
                    //System.out.println("strict precursor detected");
                    iterator++;
                    //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                }
                if (words.get(iterator).equals("graph")) {
                    //System.out.println("graph detected");
                    iterator++;
                    //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                }
                if (words.get(iterator).equals("digraph")) {
                    //System.out.println("digraph detected");
                    iterator++;
                    //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                }
                while (canInnerContinue) {
                    //System.out.println("Start of inside loop after check. Iterator is " + iterator + "and words is " + words.size() + ". Current word is " + words.get(iterator));
                    if (words.get(iterator).equals("{")) {
                        if (leftSide == null) {
                            //System.out.println("start of parts of graph");
                            iterator++;
                            //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                        } else {
                            iterator++;
                            inBracket = true;
                        }
                    } else if (words.get(iterator).equals("->")) {
                        //System.out.println("middle of dependence statement");
                        iterator++;
                        //System.out.println("iterator is: " + iterator + "and words is " + words.size());

                    }
//                    else if (words.get(iterator).equals("["))
//                    {
//                        //System.out.println("beginning of dependence list");
//                        iterator++;
//                        inBracket = true;
//                        //System.out.println("iterator is: " + iterator + "and words is " + words.size());
//                    }
//                    else if (words.get(iterator).equals("]"))
//                    {
//                        //System.out.println("end of dependence list");
//                        iterator++;
//                        inBracket = false;
//                        //System.out.println("iterator is: " + iterator + "and words is " + words.size());
//                    }
                    else if (words.get(iterator).equals(",")) {
                        iterator++;
                    } else if (words.get(iterator).equals(";")) {
                        //System.out.println("end of statement");
                        while ((line = scanner.readLine()) != null) {
                            if (line.indexOf(';') != -1 || line.indexOf('}') != -1) {
                                break;
                            }
                        }

                        match = regPattern.matcher(line);
                        words.clear();
                        while (match.find()) {
                            words.add(match.group());
                        }
                        iterator = 0;
                    } else if (words.get(iterator).equals("}")) {
                        //System.out.println("end of declaration of graph");
                        if (inBracket) {
                            iterator++;
                            inBracket = false;
                        } else {
                            canInnerContinue = false;
                        }
                    } else {
                        if (leftSide == null) // the word at hand is the left side of the statement
                        {
                            leftSide = words.get(iterator);
                            iterator++;
                            //System.out.println(leftSide + "is left side");
                        } else // the word at hand is the right side of the statement
                        {
                            rightSide = words.get(iterator);
                            //System.out.println(rightSide + "is right side");
                            iterator++;
                            MutableNode left = null;
                            MutableNode right = null;
                            for (MutableNode node : result.nodes()) {
                                if (node.name().toString().equals(leftSide)) {
                                    left = node;
                                }
                                if (node.name().toString().equals(rightSide)) {
                                    right = node;
                                }
                            }
                            if (left == null) {
                                left = mutNode(leftSide);
                                result.add(left);
                            }
                            if (right == null) {
                                right = mutNode(rightSide);
                                result.add(right);
                            }
                            //System.out.println("added new link: " + leftSide + " to " + rightSide + ".");
                            result.add(left.addLink(right));
                            if (inBracket) {
                                rightSide = null;
                            } else {
                                leftSide = null;
                                rightSide = null;
                            }
                        }
                    }
                }
                //line = scanner.readLine();

                while ((line = scanner.readLine()) != null) {
                    if (line.indexOf(';') != -1 || line.indexOf('}') != -1) {
                        break;
                    }
                }
//
                canContinue = false;
            }
        } catch (IOException e) {
            System.out.println("error with file parsing");
        }

        return result;
    }

    public static String toString(MutableGraph g) {
        int nodeCount = 0;
        int edgeCount = 0;
        String output = "Nodes List\n__________";
        for (MutableNode node : g.nodes()) {
            nodeCount++;
            output += "\nNode: " + node.name().toString() + "\n";
            output += "Edges List for node " + node.name().toString() + "\n";
            for (Link link : node.links()) {
                edgeCount++;
                output += "Edge from: " + node.name().toString() + " to " + link.to().name().toString() + "\n";
            }
        }
        output += "Number of Nodes: " + nodeCount + "\n" + "Number of Edges: " + edgeCount;
        System.out.println(output);
        return output;
    }

    public static void outputGraph(MutableGraph g, String filepath) throws IOException {
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(filepath));
    }

    //part 2 of project
    public static MutableGraph addNode(MutableGraph g, String newNodeName) {
        System.out.println("input is: " + newNodeName);
        String nodeName = "";
        for (MutableNode node : g.nodes()) {
            nodeName = node.name().toString();
            if (nodeName.equals(newNodeName)) {
                System.out.println("Node with name " + newNodeName + " already exists. Please change the node name and retry");
                return g;
            }
        }
        MutableNode insert = mutNode(newNodeName);
        g.add(insert);
        return g;
    }

    public static MutableGraph addNodes(MutableGraph g, String[] labels) {
        for (MutableNode node : g.nodes()) {
            for (String label : labels) {
                if (node.name().toString().equals(label)) {
                    System.out.println("New Node with name " + node.name().toString() + " already exists. Please change the node name and retry");
                } else {
                    MutableNode insert = mutNode(label);
                    g.add(insert);
                }
            }
        }
        return g;
    }

    public static MutableGraph addEdge(MutableGraph g, String srcLabel, String dstLabel) {
        MutableNode node1 = null;
        MutableNode node2 = null;
        for (MutableNode node : g.nodes()) {
            if (node.name().toString().equals(srcLabel)) {
                node1 = node2 = mutNode(dstLabel);
                ;
            } else if (node.name().toString().equals(dstLabel)) {
                node2 = node2 = mutNode(dstLabel);
                ;
            }
            for (Link link : node.links()) {
                if (node.name().toString().equals(srcLabel) && link.to().name().toString().equals(dstLabel)) {
                    System.out.println("Duplicate link detected. Please retry");
                    return g;
                }
            }
            if (node1 == null) {
                node1 = mutNode(srcLabel);
                g.add(node1);
            }
            if (node2 == null) {
                node2 = mutNode(dstLabel);
                g.add(node2);
            }
            g.addLink(node1, node2);
        }
        return g;
    }

    public static void outputDOTGraph(MutableGraph g, String filepath) throws IOException {
        Graphviz.fromGraph(g).render(Format.DOT).toFile(new File(filepath));
    }

    public static void outputGraphics(MutableGraph g, String filepath, String format) throws IOException {
        if (format.equalsIgnoreCase("png")) {
            Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(filepath));
        } else {
            System.out.println("File format not supported. The only current available form is PNG.");
        }
    }

    public static MutableGraph removeNode(MutableGraph g, String label) {
        MutableNode temp = g.rootNodes().stream().filter(node -> node.name().toString().equals(label)).findFirst().orElse(null);
        if (temp != null) {
            temp.links().clear();

            Iterator<MutableNode> check = g.rootNodes().iterator();
            while (check.hasNext()) {
                if (check.next().equals(temp)) {
                    check.remove();
                    break;
                }
            }
        }
        return g;
    }

    public static MutableGraph removeNodes(MutableGraph g, String[] labels) {
        for (String label : labels) {
            removeNode(g, label);
        }
        return g;
    }

    public static MutableGraph removeEdge(MutableGraph g, String srcLabel, String dstLabel) {
        MutableNode sNode = g.rootNodes().stream().filter(node -> node.name().toString().equals(srcLabel)).findFirst().orElse(null);
        MutableNode dNode = g.rootNodes().stream().filter(node -> node.name().toString().equals(dstLabel)).findFirst().orElse(null);

        if (sNode != null && dNode != null) {
            sNode.links().removeIf(link -> link.to().equals(dNode));
        }
        return g;
    }

    public static String graphSearch(MutableGraph g, Node src, Node dst, type t) {
        if (t == type.BFS) {
            String sName = src.name().toString();
            String dName = dst.name().toString();

            MutableNode sNode = g.rootNodes().stream().filter(node -> node.name().toString().equals(sName)).findFirst().orElse(null);
            MutableNode dNode = g.rootNodes().stream().filter(node -> node.name().toString().equals(dName)).findFirst().orElse(null);

            if (sNode != null || dNode != null) {
                return null;
            }

            Queue<MutableNode> queue = new LinkedList<>();
            Map<MutableNode, MutableNode> predecessors = new HashMap<>();
            Set<MutableNode> visited = new HashSet<>();

            queue.add(sNode);
            visited.add(sNode);

            while (!queue.isEmpty()) {
                MutableNode current = queue.poll();
                if (current.equals(dNode)) {
                    //output list of nodes visited
                    LinkedList<MutableNode> path = new LinkedList<>();

                    for (MutableNode at = dNode; at != null; at = predecessors.get(at)) {
                        path.addFirst(at);
                    }

                    if (!path.getFirst().equals(sNode)) {
                        return "Path between these nodes not found";
                    }

                    return path.stream()
                            .map(node -> node.name().toString())
                            .reduce((a, b) -> a + " -> " + b)
                            .orElse("");
                }

                for (Link link : current.links()) {
                    MutableNode neighbor = (MutableNode) link.to();
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        predecessors.put(neighbor, current);
                    }
                }
            }
        } else {
            String sName = src.name().toString();
            String dName = dst.name().toString();


            MutableNode sNode = g.rootNodes().stream().filter(node -> node.name().toString().equals(sName)).findFirst().orElse(null);
            MutableNode dNode = g.rootNodes().stream().filter(node -> node.name().toString().equals(sName)).findFirst().orElse(null);


            Stack<MutableNode> stack = new Stack<>();
            Map<MutableNode, MutableNode> predecessors = new HashMap<>();
            Set<MutableNode> visited = new HashSet<>();


            while (!stack.isEmpty()) {
                MutableNode current = stack.pop();


                if (current.equals(dNode)) {
                    //output list of nodes visited
                    LinkedList<MutableNode> path = new LinkedList<>();


                    for (MutableNode at = dNode; at != null; at = predecessors.get(at)) {
                        path.addFirst(at);
                    }


                    if (!path.getFirst().equals(sNode)) {
                        return null; // Return null if there's no valid path from start to target
                    }


                    return path.stream()
                            .map(node -> node.name().toString())
                            .reduce((a, b) -> a + " -> " + b)
                            .orElse("");
                }
                for (Link link : current.links()) {
                    MutableNode neighbor = (MutableNode) link.to();


                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        visited.add(neighbor);
                        predecessors.put(neighbor, current);
                    }
                }

            }
            return "Destination Node not found";
        }
        return "Destination Node not found";
    }
}