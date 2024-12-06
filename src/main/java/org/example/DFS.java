package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DFS extends Search implements Strategy
{
    public DFS(MutableGraph g, MutableNode sNode, MutableNode dNode)
    {
        super(g, sNode, dNode);
    }
    @Override
    public List<MutableNode> search()
    {
        while(!sq.isEmpty())
        {
            List<MutableNode> path =  sq.removeLast();
            MutableNode current = path.get(path.size()-1);
            if(current == null)
            {
                System.out.println("current node not found\n");
            }
            if(current.equals(dNode))
            {
                return path;
            }
            exploreNeighbors(current, path);
        }
        return null;
    }
    @Override
    protected void addPath(List<MutableNode> path)
    {
        sq.add(path);
    }
}
