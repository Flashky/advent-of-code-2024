package com.adventofcode.flashk.day21;

import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedMultigraph;


public class DirectionalKeypad {

    public static final Character UP = '^';
    public static final Character DOWN = 'v';
    public static final Character LEFT = '<';
    public static final Character RIGHT = '>';
    public static final Character ACCEPT = 'A';
    public static final String ACCEPT_TO_LEFT = "v<<";
    public static final String LEFT_TO_ACCEPT = "^>>";
    public static final String DOWN_TO_ACCEPT = ">^";

    private Graph<Character,PadEdge> graph = new DirectedMultigraph<>(PadEdge.class);
    private DijkstraShortestPath<Character,PadEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

    @Setter
    private Character currentKey = ACCEPT;

    public DirectionalKeypad() {
        graph.addVertex(LEFT);
        graph.addVertex(DOWN);
        graph.addVertex(UP);
        graph.addVertex(RIGHT);
        graph.addVertex(ACCEPT);

        graph.addEdge(LEFT, DOWN, new PadEdge(RIGHT));
        graph.addEdge(LEFT,ACCEPT, new PadEdge(LEFT_TO_ACCEPT));

        graph.addEdge(DOWN, LEFT, new PadEdge(LEFT));
        graph.addEdge(DOWN, UP, new PadEdge(UP));
        graph.addEdge(DOWN, RIGHT, new PadEdge(RIGHT));
        graph.addEdge(DOWN, ACCEPT, new PadEdge(DOWN_TO_ACCEPT));

        graph.addEdge(UP, DOWN, new PadEdge(DOWN));
        graph.addEdge(UP, ACCEPT, new PadEdge(RIGHT));

        graph.addEdge(RIGHT, DOWN, new PadEdge(LEFT));
        graph.addEdge(RIGHT, ACCEPT, new PadEdge(UP));

        graph.addEdge(ACCEPT, UP, new PadEdge(LEFT));
        graph.addEdge(ACCEPT, RIGHT, new PadEdge(DOWN));
        graph.addEdge(ACCEPT, LEFT, new PadEdge(ACCEPT_TO_LEFT)); // new

    }

    /// Enters a code, such as `029A`, returning a [String] representing the needed moves for it.
    /// @param code the code to enter.
    /// @return The String that represents the performed movements to enter that code at the numpad.
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
}
