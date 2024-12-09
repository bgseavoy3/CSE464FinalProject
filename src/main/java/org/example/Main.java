package org.example;
import java.io.*;
import java.util.*;
import java.util.regex.*;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the name of your desired dot file (Must be in the file location src/java/resources/)");
        String filePath = sc.nextLine();
        MutableGraph result = null;
        try
        {
            result = parseGraph(filePath);
        }
        catch(IOException e)
        {
            System.out.println("Could not read file");
        }
        assert result != null;
        System.out.println("File is parsed successfully");
        boolean exit = false;
        String input;
        while (!exit) {
            System.out.println("Please enter the number for what would would like to do\n 1: print out graph info\n2: add a new node\n3: add a new edge\n4: output graph to a file location\n5: Perform a BFS to find a path between 2 nodes\n6: Perform a DFS to find a path between nodes\n7: Perform the random walk search to find a path between two nodes\n8: exit\n9: remove one or multiple nodes\n10: remove an edge");
            input = sc.nextLine();
            if (input.equals("1")) {
                Main.toString(result);
            }
            if (input.equals("2")) {
                System.out.println("Type 1 for 1 entry, 2 for multiple");
                input = sc.nextLine();
                if (input.equals("1")) {
                    System.out.println("Enter new node name");
                    input = sc.nextLine();
                    Main.addNode(result, input);
                } else {
                    System.out.println("Enter new node names and press enter after each to submit, enter \"exit\" to stop entering names");
                    while (!(input.equals("exit"))) {
                        input = sc.nextLine();
                        if (input.equals("exit")) {
                            break;
                        }
                        else {
                            Main.addNode(result, input);
                        }
                    }
                }
            }
            if (input.equals("3")) {
                System.out.println("Enter source node name");
                String input1 = sc.nextLine();
                System.out.println("Enter destination node name");
                String input2 = sc.nextLine();
                Main.addEdge(result, input1, input2);
            }
            if (input.equals("4")) {
                System.out.println("Enter file output name");
                String input1 = sc.nextLine();
                System.out.println("Enter output type. Current forms available are dot and png");
                String input2 = sc.nextLine();
                if (input2.equals("dot")) {
                    Main.outputGraph(result, input1);
                } else {
                    Main.outputGraphics(result, input1, input2);
                }
            }
            if (input.equals("5"))
            {
                System.out.println("Type the name of the starting node");
                String input1 = sc.nextLine();
                System.out.println("Enter the name of the destination node");
                String input2 = sc.nextLine();
                MutableNode node1 = null;
                MutableNode node2 = null;
                for (MutableNode node : result.nodes()) {
                    if (node.name().toString().equals(input1)) {
                        node1 = node;
                    } else if (node.name().toString().equals(input2)) {
                        node2 = node;
                    }
                }
                System.out.println("Path: " + graphSearch(result, node1, node2, Algorithm.BFS));

            }
            if (input.equals("6"))
            {
                System.out.println("Type the name of the starting node");
                String input1 = sc.nextLine();
                System.out.println("Enter the name of the destination node");
                String input2 = sc.nextLine();
                MutableNode node1 = null;
                MutableNode node2 = null;
                for (MutableNode node : result.nodes()) {
                    if (node.name().toString().equals(input1)) {
                        node1 = node;
                    } else if (node.name().toString().equals(input2)) {
                        node2 = node;
                    }
                }
                System.out.println("Path: " + graphSearch(result, node1, node2, Algorithm.DFS));
            }
            if (input.equals("7"))
            {
                System.out.println("Type the name of the starting node");
                String input1 = sc.nextLine();
                System.out.println("Enter the name of the destination node");
                String input2 = sc.nextLine();
                MutableNode sNode = result.rootNodes().stream().filter(node -> node.name().toString().equals(input1)).findFirst().orElse(null);
                MutableNode dNode = result.rootNodes().stream().filter(node -> node.name().toString().equals(input2)).findFirst().orElse(null);
                walkSearch(result,sNode,dNode);
            }
            if (input.equals("8"))
            {
                exit = true;
            }
            if (input.equals("9"))
            {
                System.out.println("Type 1 to remove 1 node and 2 to remove multiple");
                String input1 = sc.nextLine();
                if(input1.equals("1"))
                {
                    System.out.println("Type the name of the node");
                    String input2 = sc.nextLine();
                    removeNode(result, input2);
                }
                if(input1.equals("2"))
                {
                    boolean loopExit = false;
                    while(loopExit == false)
                    {
                        System.out.println("Type the name of the node and then press enter. Type exit to stop removing nodes");
                        String input2 = sc.nextLine();
                        if(input2.equals("exit"))
                        {
                            loopExit = true;
                        }
                        else
                        {
                            removeNode(result, input2);
                        }
                    }
                }
            }
            if(input.equals("10"))
            {
                System.out.println("Enter source node name");
                String input1 = sc.nextLine();
                System.out.println("Enter destination node name");
                String input2 = sc.nextLine();
                Main.removeEdge(result, input1, input2);
            }
        }
    }

    public static MutableGraph parseGraph(String filepath) throws IOException
    {
        //System.out.println("made it into method");
        MutableGraph result = mutGraph("test").setDirected(true);
        int iterator = 0;
        //File file = new File(filepath);

        InputStream file = Main.class.getClassLoader().getResourceAsStream(filepath);

        assert file != null;
        BufferedReader scanner = new BufferedReader(new InputStreamReader(file));
        String leftSide = null;
        String rightSide = null;
        boolean canContinue = true;
        boolean canInnerContinue = true;
        boolean inBracket = false;
        //System.out.println("made it through init");
        String line = "";
        int current;
        char c;
        ArrayList<String> words;
        line = readALine(scanner);
        //System.out.println("line is: " + line);
        words = findWords(line);
        //System.out.println(line + " is the current line");
        //System.out.println("made it through split");
        try
        {
            while (canContinue)
            {
                //System.out.println("Start of outside loop. Iterator is " + iterator);
                iterator = checkType(words.get(iterator), iterator);
                while (canInnerContinue)
                {
                    //System.out.println("Start of inside loop after check. Iterator is " + iterator + "and words is " + words.size() + ". Current word is " + words.get(iterator));
                    int currentWord = typeOfInput(words.get(iterator));
                    if (currentWord == 1)
                    {
                        if (leftSide == null)
                        {
                            //System.out.println("start of parts of graph");
                            iterator++;
                            //System.out.println("iterator is: " + iterator + "and words is " + words.size());
                        }
                        else {
                            iterator++;
                            inBracket = true;
                        }
                    }
                    else if (currentWord == 2)
                    {
                        //System.out.println("middle of dependence statement");
                        iterator++;
                        //System.out.println("iterator is: " + iterator + "and words is " + words.size());

                    }
                    else if (currentWord == 3)
                    {
                        iterator++;
                    }
                    else if (currentWord == 4)
                    {
                        //System.out.println("end of statement");
                        line = readALine(scanner);
                        words = findWords(line);
                        iterator = 0;
                    }
                    else if (currentWord == 5)
                    {
                        //System.out.println("end of declaration of graph");
                        if (inBracket)
                        {
                            iterator++;
                            inBracket = false;
                        }
                        else
                        {
                            canInnerContinue = false;
                        }
                    }
                    else
                    {
                        if (leftSide == null) // the word at hand is the left side of the statement
                        {
                            leftSide = words.get(iterator);
                            iterator++;
                            //System.out.println(leftSide + "is left side");
                        }
                        else // the word at hand is the right side of the statement
                        {
                            rightSide = words.get(iterator);
                            //System.out.println(rightSide + "is right side");
                            iterator++;
                            MutableNode left = null;
                            MutableNode right = null;
                            for (MutableNode node : result.nodes())
                            {
                                if (node.name().toString().equals(leftSide))
                                {
                                    left = node;
                                }
                                if (node.name().toString().equals(rightSide))
                                {
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
                line = readALine(scanner);
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
                node1 = node;
            } else if (node.name().toString().equals(dstLabel)) {
                node2 = node;
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
        for (Link link : node1.links()) {
            if (link.to().name().toString().equals(dstLabel)) {
                System.out.println("Duplicate link detected. Please retry");
                return g;
            }
        }
        node1.addLink(node2);
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
            while (check.hasNext())
            {
                if (check.next().equals(temp))
                {
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

    public static String graphSearch(MutableGraph g, MutableNode src, MutableNode dst, Algorithm t) {
        String result; // USE STRATEGY.SEARCH()
        if (t == Algorithm.BFS)
        {
            Strategy temp  = new BFS(g, src, dst);
            System.out.println("Conducting BFS");
            result = Search.listToString(temp.search());
        }
        else
        {
            Strategy temp  = new DFS(g, src, dst);
            System.out.println("Conducting DFS");
            result = Search.listToString(temp.search());
        }
        return result;
    }
    public static void walkSearch(MutableGraph g, MutableNode src, MutableNode dst)
    {
        boolean notFound = true;
        LinkedList<MutableNode> trail = new LinkedList<>();
        LinkedList<MutableNode> checked = new LinkedList<>();
        MutableNode temp = src;
        trail.add(temp);
        checked.add(temp);
        int numOfChecked = 0;
        while(notFound)
        {
            randomToString(trail);
            MutableNode temp2 = getRandomNeighbor(g, temp);
            assert temp2 != null;
            if(temp2 == dst)
            {
                trail.add(temp2);
                randomToString(trail);
                return;
            }
            if(isChecked(temp2, checked))
            {
                //is already looked at, don't do anything
                if(temp.links().size() <= 1)
                {
                    //if this is the only option, go back a node
                    if(trail.size() > 1)
                    {
                        trail.remove(trail.getLast());
                        temp = trail.getLast();
                        numOfChecked = 1;
                    }
                    else
                    {
                        System.out.println("error, no link found");
                        return;
                    }
                }
                else
                {
                    numOfChecked++;
                }
                if(isAllChecked(g, temp, checked))
                {
                    trail.remove(trail.getLast());
                    temp = trail.getLast();
                    numOfChecked = 1;
                }
            }
            else {
                if (numOfChecked == temp.links().size())
                {
                    //checked all neighbors of current node, must take a step back
                    if(trail.size() > 1)
                    {
                        trail.remove(trail.getLast());
                        temp = trail.getLast();
                        numOfChecked = 1;
                    }
                    else
                    {
                        System.out.println("error, no link found");
                        return;
                    }
                }
                else if (temp2.links().isEmpty())
                {
                    // no links to go to, must find another node
                    trail.remove(trail.getLast());
                    temp = trail.getLast();
                    numOfChecked = 1;
                }
                else
                {
                    //new node to check. go to new node
                    temp = temp2;
                    trail.add(temp);
                    checked.add(temp);
                    numOfChecked = 0;
                }
            }


        }
    }
    //helper methods
    public static ArrayList<String> findWords(String line)
    {
        ArrayList<String> words = new ArrayList<>();
        Matcher match;
        String regExpression = "\\w+|->|[{}\\[\\];,]";
        Pattern regPattern = Pattern.compile(regExpression);
        match = regPattern.matcher(line);
        while (match.find()) {
            words.add(match.group());
        }
        return words;
    }
    public static String readALine(BufferedReader scanner) throws IOException {
        String line;
        while ((line = scanner.readLine()) != null) {
            if (line.indexOf(';') != -1 || line.indexOf('}') != -1) {
                break;
            }
        }
        return line;
    }
    public static int checkType(String word, int iterator)
    {
        if (word.equals("strict")) {
            //System.out.println("strict precursor detected");
            iterator++;
            //System.out.println("iterator is: " + iterator + "and words is " + words.size());
        }
        if (word.equals("graph")) {
            //System.out.println("graph detected");
            iterator++;
            //System.out.println("iterator is: " + iterator + "and words is " + words.size());
        }
        if (word.equals("digraph")) {
            //System.out.println("digraph detected");
            iterator++;
            //System.out.println("iterator is: " + iterator + "and words is " + words.size());
        }
        return iterator;
    }
    public static int typeOfInput(String word)
    {
        if(word.equals("{"))
        {
            return 1;
        }
        else if(word.equals("->"))
        {
            return 2;
        }
        else if(word.equals(","))
        {
            return 3;
        }
        else if(word.equals(";"))
        {
            return 4;
        }
        if(word.equals("}"))
        {
            return 5;
        }
        else
        {
            return 6;
        }
    }
    public static String DoBFS(MutableGraph g, MutableNode src, MutableNode dst)
    {
        BFS bfs = new BFS(g, src, dst);
        return bfs.Path();
    }
    public static String DoDFS(MutableGraph g, MutableNode src, MutableNode dst)
    {
        DFS dfs = new DFS(g, src, dst);
        return dfs.Path();
    }
    public static MutableNode getRandomNeighbor(MutableGraph g, MutableNode n) {
        List<Link> links = new ArrayList<>(n.links());
        if (links.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * links.size());
        Link randomLink = links.get(randomIndex);

        Label targetName = randomLink.to().name();

        return g.nodes().stream()
                .filter(node -> node.name().equals(targetName))
                .findFirst()
                .orElse(null);
    }
    public static void randomToString(LinkedList<MutableNode> l)
    {
        String str = "visiting Path{nodes=[";;
        for (int i = 0; i < l.size(); i++)
        {
            if(i < l.size() - 1)
            {
                str = str.concat("Node{" + l.get(i).name().toString() + "},");
            }
            else
            {
                str = str.concat("Node{" + l.get(i).name().toString() + "}]}");

            }
        }
        System.out.println(str);
    }
    public static boolean isChecked(MutableNode n, LinkedList<MutableNode> checked)
    {
        for(MutableNode node : checked)
        {
            if(node.name().toString().equals(n.name().toString()))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isAllChecked(MutableGraph g, MutableNode n, LinkedList<MutableNode> checked)
    {
        for(Link link : n.links())
        {
            Label nodeName = link.to().name();
            MutableNode targetName = g.nodes().stream().filter(node -> node.name().equals(nodeName)).findFirst().orElse(null);;
            if(!isChecked(targetName, checked))
            {
                return false;
            }
        }
        return true;
    }
}