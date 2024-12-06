package org.example;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;

import java.util.List;

public interface Strategy {
    List<MutableNode> search();
}


