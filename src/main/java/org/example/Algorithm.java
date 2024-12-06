package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;

public enum Algorithm
{
    BFS {
        @Override
        public Strategy getStrategy(MutableGraph g, MutableNode sNode, MutableNode dNode)
        {
            return new BFS(g, sNode, dNode);
        }
    },

    DFS {
        @Override
        public Strategy getStrategy(MutableGraph g, MutableNode sNode, MutableNode dNode)
        {
            return new DFS(g, sNode, dNode);
        }
    };

    public abstract Strategy getStrategy(MutableGraph g, MutableNode sNode, MutableNode dNode);
}
