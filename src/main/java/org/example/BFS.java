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
    protected void addPath(List<MutableNode> path)
    {
        sq.add(path);
    }
    @Override
    protected List<MutableNode> getPoll()
    {
        return sq.poll();
    }
}
