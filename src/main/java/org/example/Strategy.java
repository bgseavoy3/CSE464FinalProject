package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.util.List;

public interface Strategy {
    List<MutableNode> search();
    public enum Algorithm
    {
        BFS {

                public Strategy getStrategy(MutableGraph g, MutableNode sNode, MutableNode dNode)
                {
                    return new BFS(g, sNode, dNode);
                }
            },

        DFS {

            public Strategy getStrategy(MutableGraph g, MutableNode sNode, MutableNode dNode)
            {
                return new DFS(g, sNode, dNode);
            }
        }
    }
    public abstract Strategy getStrategy(MutableGraph g, MutableNode sNode, MutableNode dNode);
}
