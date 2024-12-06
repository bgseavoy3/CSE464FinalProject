package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BFS extends Search implements Strategy
{
    public BFS(MutableGraph g, MutableNode sNode, MutableNode dNode)
    {
        super(g, sNode, dNode);
    }
    @Override
    public List<MutableNode> search()
    {
        while(!sq.isEmpty())
        {
            List<MutableNode> path = sq.poll();
            MutableNode current = path.get(path.size()-1);
            System.out.println("Current node: " + current.name());
            System.out.println("Current path: " + listToString(path));
            if(current == null)
            {
                System.out.println("current node not found\n");
            }
            else if(current.name().equals(dNode.name()))
            {
                System.out.println("Destination node found\n");
                return path;
            }
            exploreNeighbors(current, path);
        }
        System.out.println("No path found\n");
        return null;
    }
    @Override
    protected void addPath(List<MutableNode> path)
    {
        sq.add(path);
    }

}
