package com.adventofcode.flashk.day21.redesign;

import com.adventofcode.flashk.common.Input;
import com.adventofcode.flashk.common.jgrapht.LabeledEdge;
import com.adventofcode.flashk.day25.Key;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Keypad {

    private static final String FILE_DIRECTIONAL_MAPPINGS = "directional_mappings.txt";
    private static final String FILE_NUMPAD_MAPPINGS = "numpad_mappings.txt";

    private final Graph<String, LabeledEdge> graph = new DirectedMultigraph<>(LabeledEdge.class);
    private final boolean directional;
    private static final Map<KeypadPress,List<String>> memo = new HashMap<>();

    // TODO opción 2: caché a nivel de code en lugar de button

    private String currentButton = "A";


    public Keypad(boolean isDirectional) {

        // Create the graph using mappings files

        // Each line of the mappings files is a comma delimited list which represents:
        // source_vertex,edge_label,target_vertex

        // This allows to create a complete graph based on the contents of the graph.
        directional = isDirectional;

        // Read the key mappings from the file
        String filename = directional ? FILE_DIRECTIONAL_MAPPINGS : FILE_NUMPAD_MAPPINGS;
        List<String> lines = Input.readStringLines("day21", filename);

        // Create the vertexes and edges described at each line
        for(String line : lines) {

            String[] parts = line.split(",");
            String vertexSource =  parts[0];
            String edgeLabel = parts[1];
            String vertexTarget = parts[2];

            if(!graph.containsVertex(vertexSource)) {
                graph.addVertex(vertexSource);
            }

            if(!graph.containsVertex(vertexTarget)) {
                graph.addVertex(vertexTarget);
            }

            graph.addEdge(vertexSource, vertexTarget, new LabeledEdge(edgeLabel));

        }

    }

    /// Enters a code to be pressed at the pad returning a [String] representing the needed moves for it.
    ///
    /// The code can be either an alphanumeric value as such as `029A` to be pressed at a numeric pad or an
    /// alphanumeric value such as `<A^A^>^AvvvA` to be pressed at a directional keypad.
    ///
    /// @param code the code to enter.
    /// @return A list of possible instructions to be executed by the next robot
    public Set<String> press(String code) {
        char[] buttons = code.toCharArray();

        List<List<String>> allButtonPaths = new ArrayList<>();
        for(char button : buttons) {

            if(directional) {
                button = switch(button) {
                    case '<' -> 'L';
                    case '>' -> 'R';
                    case '^' -> 'U';
                    case 'v' -> 'D';
                    case 'A' -> 'A';
                    default -> throw new IllegalStateException("Unexpected button value: " + button);
                };
            }

            // Generate a memoization status for this key press
            KeypadPress state = new KeypadPress(currentButton, button);

            // Obtain from memo or compute. Add it to memoization if it didn't exist.
            List<String> buttonPaths = memo.getOrDefault(state, press(button));
            memo.putIfAbsent(state, buttonPaths);

            //
            allButtonPaths.add(buttonPaths);
        }

        // Create all the possible path combinations
        List<List<String>> cartesianList = Lists.cartesianProduct(allButtonPaths);
        Set<String> paths = new HashSet<>();
        for(List<String> possiblePaths : cartesianList) {
            paths.add(String.join(StringUtils.EMPTY, possiblePaths));
        }

        return paths;
    }

    /// Presses the specified button retrieving a list of minimum cost directional key presses to achieve that key press.
    ///
    /// @param button the button to press.
    /// @return a list of different paths as String to achieve that key press from the current button position.
    public List<String> press(char button) {
        AllDirectedPaths<String, LabeledEdge> adp = new AllDirectedPaths<>(graph);

        List<GraphPath<String, LabeledEdge>> graphPaths = adp.getAllPaths(currentButton, String.valueOf(button),
                                                                true,4);

        int shortestPathSize = Integer.MAX_VALUE;
        
        List<String> paths = new ArrayList<>();
        for(GraphPath<String, LabeledEdge> graphPath : graphPaths) {
            String path = graphPath.getEdgeList().stream().map(LabeledEdge::getValue).collect(Collectors.joining()) + "A";
            shortestPathSize = Math.min(path.length(), shortestPathSize);
            paths.add(path);
        }

        this.currentButton = String.valueOf(button);
        int finalShortestPathSize = shortestPathSize;
        return paths.stream().filter(p -> p.length() == finalShortestPathSize).toList();
    }

    public void paint() {

        DOTExporter<String, LabeledEdge> exporter = new DOTExporter<>(v -> v);
        exporter.setEdgeAttributeProvider(e -> Map.of("label", e));

        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        System.out.println(writer.toString());
    }
}
