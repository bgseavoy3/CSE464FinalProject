package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

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
            if(current == null)
            {
                System.out.println("current node not found\n");
            }
            else if(current.equals(dNode))
            {
                return path;
            }
            exploreNeighbors(current, path);
        }
        return null;
    }
    public Strategy getStrategy(MutableGraph g, MutableNode sNode, MutableNode dNode)
    {
        return new BFS(g, sNode, dNode);
    }
}
