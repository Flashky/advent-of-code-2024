package com.adventofcode.flashk.day21;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.List;

public class Numpad {

    public static final Character BUTTON_0 = '0';
    public static final Character BUTTON_1 = '1';
    public static final Character BUTTON_2 = '2';
    public static final Character BUTTON_3 = '3';
    public static final Character BUTTON_4 = '4';
    public static final Character BUTTON_5 = '5';
    public static final Character BUTTON_6 = '6';
    public static final Character BUTTON_7 = '7';
    public static final Character BUTTON_8 = '8';
    public static final Character BUTTON_9 = '9';
    public static final Character ACCEPT = 'A';
    private static final String BUTTON_2_TO_BUTTON_9 = ">^^";
    private static final String BUTTON_9_TO_BUTTON_2 = "vv<";

    private Graph<Character,PadEdge> graph = new DirectedWeightedMultigraph<>(PadEdge.class);

    private DijkstraShortestPath<Character,PadEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

    private Character currentKey = ACCEPT;

    public Numpad() {

        graph.addVertex(BUTTON_0);
        graph.addVertex(BUTTON_1);
        graph.addVertex(BUTTON_2);
        graph.addVertex(BUTTON_3);
        graph.addVertex(BUTTON_4);
        graph.addVertex(BUTTON_5);
        graph.addVertex(BUTTON_6);
        graph.addVertex(BUTTON_7);
        graph.addVertex(BUTTON_8);
        graph.addVertex(BUTTON_9);
        graph.addVertex(ACCEPT);


        // TODO problema con los ejes
        // Labeled edges in multigraph MUST BE unique, so an String or Character is not enough:
        // https://jgrapht.org/guide/VertexAndEdgeTypes#labeled-edges-in-a-multigraph

        // modelar ejes con un record único.


        // cada eje debería tener un nombre único
        PadEdge padEdge;

        graph.addEdge(BUTTON_0, BUTTON_2, new PadEdge(DirectionalKeypad.UP));
        graph.setEdgeWeight(BUTTON_0, BUTTON_2, 2);

        graph.addEdge(BUTTON_0, ACCEPT,   new PadEdge(DirectionalKeypad.RIGHT));
        graph.setEdgeWeight(BUTTON_0, ACCEPT, 1);

        graph.addEdge(BUTTON_1, BUTTON_2, new PadEdge(DirectionalKeypad.RIGHT));
        graph.setEdgeWeight(BUTTON_1, BUTTON_2, 2);

        graph.addEdge(BUTTON_1, BUTTON_4, new PadEdge(DirectionalKeypad.UP));
        graph.setEdgeWeight(BUTTON_1, BUTTON_4, 3);

        graph.addEdge(BUTTON_2, BUTTON_0, new PadEdge(DirectionalKeypad.DOWN));
        graph.setEdgeWeight(BUTTON_2, BUTTON_0, 2);

        graph.addEdge(BUTTON_2, BUTTON_1, new PadEdge(DirectionalKeypad.LEFT));
        graph.setEdgeWeight(BUTTON_2, BUTTON_1, 3);

        graph.addEdge(BUTTON_2, BUTTON_3, new PadEdge(DirectionalKeypad.RIGHT));
        graph.setEdgeWeight(BUTTON_2, BUTTON_3, 1);

        graph.addEdge(BUTTON_2, BUTTON_5, new PadEdge(DirectionalKeypad.UP));
        graph.setEdgeWeight(BUTTON_2, BUTTON_5, 2);

        graph.addEdge(BUTTON_3, BUTTON_2, new PadEdge(DirectionalKeypad.LEFT));
        graph.setEdgeWeight(BUTTON_3, BUTTON_2, 2);

        graph.addEdge(BUTTON_3, BUTTON_6, new PadEdge(DirectionalKeypad.UP));
        graph.setEdgeWeight(BUTTON_3, BUTTON_6, 1);

        graph.addEdge(BUTTON_3, ACCEPT,   new PadEdge(DirectionalKeypad.DOWN));
        graph.setEdgeWeight(BUTTON_3, ACCEPT, 1);

        graph.addEdge(BUTTON_4, BUTTON_1, new PadEdge(DirectionalKeypad.DOWN));
        graph.setEdgeWeight(BUTTON_4, BUTTON_1, 3);

        graph.addEdge(BUTTON_4, BUTTON_5, new PadEdge(DirectionalKeypad.RIGHT));
        graph.setEdgeWeight(BUTTON_4, BUTTON_5, 2);

        graph.addEdge(BUTTON_4, BUTTON_7, new PadEdge(DirectionalKeypad.UP));
        graph.setEdgeWeight(BUTTON_4, BUTTON_7, 3);

        graph.addEdge(BUTTON_5, BUTTON_2, new PadEdge(DirectionalKeypad.DOWN));
        graph.setEdgeWeight(BUTTON_5, BUTTON_2, 2);

        graph.addEdge(BUTTON_5, BUTTON_4, new PadEdge(DirectionalKeypad.LEFT));
        graph.setEdgeWeight(BUTTON_5, BUTTON_4, 3);

        graph.addEdge(BUTTON_5, BUTTON_6, new PadEdge(DirectionalKeypad.RIGHT));
        graph.setEdgeWeight(BUTTON_5, BUTTON_6, 1);

        graph.addEdge(BUTTON_5, BUTTON_8, new PadEdge(DirectionalKeypad.UP));
        graph.setEdgeWeight(BUTTON_5, BUTTON_8, 2);

        graph.addEdge(BUTTON_6, BUTTON_3, new PadEdge(DirectionalKeypad.DOWN));
        graph.setEdgeWeight(BUTTON_6, BUTTON_3, 1);

        graph.addEdge(BUTTON_6, BUTTON_5, new PadEdge(DirectionalKeypad.LEFT));
        graph.setEdgeWeight(BUTTON_6, BUTTON_5, 2);

        graph.addEdge(BUTTON_6, BUTTON_9, new PadEdge(DirectionalKeypad.UP));
        graph.setEdgeWeight(BUTTON_6, BUTTON_9, 1);

        graph.addEdge(BUTTON_7, BUTTON_4, new PadEdge(DirectionalKeypad.DOWN));
        graph.setEdgeWeight(BUTTON_7, BUTTON_4, 3);

        graph.addEdge(BUTTON_7, BUTTON_8, new PadEdge(DirectionalKeypad.RIGHT));
        graph.setEdgeWeight(BUTTON_7, BUTTON_8, 2);

        graph.addEdge(BUTTON_8, BUTTON_5, new PadEdge(DirectionalKeypad.DOWN));
        graph.setEdgeWeight(BUTTON_8, BUTTON_5, 2);

        graph.addEdge(BUTTON_8, BUTTON_7, new PadEdge(DirectionalKeypad.LEFT));
        graph.setEdgeWeight(BUTTON_8, BUTTON_7, 3);

        graph.addEdge(BUTTON_8, BUTTON_9, new PadEdge(DirectionalKeypad.RIGHT));
        graph.setEdgeWeight(BUTTON_8, BUTTON_9, 1);

        graph.addEdge(BUTTON_9, BUTTON_6, new PadEdge(DirectionalKeypad.DOWN));
        graph.setEdgeWeight(BUTTON_9, BUTTON_6, 1);

        graph.addEdge(BUTTON_9, BUTTON_8, new PadEdge(DirectionalKeypad.LEFT));
        graph.setEdgeWeight(BUTTON_9, BUTTON_8, 2);
        //graph.addEdge(BUTTON_9, BUTTON_2, new PadEdge(BUTTON_9_TO_BUTTON_2));

        graph.addEdge(ACCEPT, BUTTON_0,   new PadEdge(DirectionalKeypad.LEFT));
        graph.setEdgeWeight(ACCEPT, BUTTON_0, 1);

        graph.addEdge(ACCEPT, BUTTON_3,   new PadEdge(DirectionalKeypad.UP));
        graph.setEdgeWeight(ACCEPT, BUTTON_3, 1);


    }

    /// Enters a code, such as `029A`, returning a [String] representing the needed moves for it.
    /// @param code the code to enter.
    /// @return The String the instructions to be executed at a [DirectionalKeypad](DirectionalKeypad).
    public String press(String code) {
        char[] buttons = code.toCharArray();

        StringBuilder sb = new StringBuilder();
        for(char button : buttons) {
            sb.append(press(button));
        }

        return sb.toString();
    }

    private String press(char button) {
        GraphPath<Character, PadEdge> path = dijkstraShortestPath.getPath(currentKey, button);
        StringBuilder sb = new StringBuilder();
        for(PadEdge edge : path.getEdgeList()) {
            sb.append(edge.label());
        }
        sb.append(ACCEPT);
        currentKey = button;
        return sb.toString();
    }


    public void reset() {
        currentKey = ACCEPT;
    }
}
