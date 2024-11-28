package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.util.*;

public abstract class Search
{
    protected final MutableGraph g;
    protected final MutableNode sNode;
    protected final MutableNode dNode;
    protected Set<MutableNode> visited;
    protected Deque<List<MutableNode>> sq;

    public Search(MutableGraph g, MutableNode sNode, MutableNode dNode)
    {
        this.g = g;
        this.sNode = sNode;
        this.dNode = dNode;
        initialize();
    }
    public final String Path()
    {
        initialize();
        List<MutableNode> result = search();
        return listToString(result);
    }
    public final void initialize()
    {
        visited = new HashSet<>();
        sq = new LinkedList<>();
        sq.add(Collections.singletonList(sNode));
        visited.add(sNode);
    }
    protected abstract List<MutableNode> search();

    protected List<MutableNode> getNeighbors(MutableNode node)
    {
        List<MutableNode> result = new ArrayList<>();
        node.links().forEach(link -> result.add((MutableNode) link.to()));
        return result;
    }
    protected void exploreNeighbors(MutableNode current, List<MutableNode> path)
    {
        for(MutableNode n : getNeighbors(current))
        {
            if (!visited.contains(n))
            {
                visited.add(n);
                List<MutableNode> nPath = new ArrayList<>(path);
                nPath.add(n);
                if (this instanceof BFS)
                {
                    sq.add(nPath);
                }
                else
                {
                    sq.addFirst(nPath);
                }
            }
        }
    }
    protected String listToString(List<MutableNode> list)
    {
        String result = "";
        while(!list.isEmpty())
        {
            result += list.remove(0).toString();
            if(!list.isEmpty())
            {
                result += " -> ";
            }
        }
        return result;
    }

}
