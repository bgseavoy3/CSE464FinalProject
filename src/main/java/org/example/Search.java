package org.example;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;

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

    protected abstract void addPath(List<MutableNode> path);

    protected List<MutableNode> getNeighbors(MutableNode node)
    {
        List<MutableNode> result = new ArrayList<>();
        node.links().forEach(link -> {

            MutableNode toNode = findNode(link.to().name());
            result.add(toNode);
        });
        return result;
    }
    protected void exploreNeighbors(MutableNode current, List<MutableNode> path)
    {
        for (MutableNode n : getNeighbors(current)) {
            if (!visited.contains(n)) {
                visited.add(n);
                ArrayList<MutableNode> nPath = new ArrayList<>(path);
                nPath.add(n);
                addPath(nPath);
            }
        }
    }
    protected String listToString(List<MutableNode> list) {
        StringBuilder result = new StringBuilder();
        if(list == null)
        {
            result.append("destination node not found\n");
        }
        for (int i = 0; i < list.size(); i++)
        {
            result.append(list.get(i).name());
            if (i < list.size() - 1)
            {
                result.append(" -> ");
            }
        }

        return result.toString();
    }
    protected MutableNode findNode(Label name)
    {
        MutableNode fNode = g.rootNodes().stream().filter(node -> node.name().equals(name)).findFirst().orElse(null);
        return fNode;
    }

}
