package com.adventofcode.flashk.day24;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CrossedWiresVisual {

    private static final Pattern GATE_PATTERN = Pattern.compile("(\\w*) (AND|XOR|OR) (\\w*) -> (\\w*)");

    private final Graph<GateVisual, String> graph = new DirectedMultigraph<>(String.class);

    private final Map<String,GateVisual> gatesPerOutput = new HashMap<>();
    private final Map<String,Integer> wires = new HashMap<>();
    private final List<GateVisual> gates = new ArrayList<>();
    private List<String> endWires;

    public CrossedWiresVisual(List<String> inputs) {
        initializeWires(inputs);
        initializeGates(inputs);
        createStartEndVertexes();
        connectGates();
    }

    private void connectGates() {
        // Por cada puerta
        // Buscar si existe en gatesPerOutput
        for(GateVisual gate : gatesPerOutput.values()) {
            String output = gate.getOutput();

            // Search gates that have this output gate as input 1.
            List<GateVisual> gatesInput1 = gates.stream().filter(g -> output.equals(g.getInput1())).toList();
            for(GateVisual gateInput1 : gatesInput1) {
                String edgeId = output + "_" + UUID.randomUUID();
                graph.addEdge(gate, gateInput1, edgeId);
            }

            // Search gates that have this output gate as input 2.
            List<GateVisual> gatesInput2 = gates.stream().filter(g -> output.equals(g.getInput2())).toList();
            for(GateVisual gateInput2 : gatesInput2) {
                String edgeId = output + "_" + UUID.randomUUID();
                graph.addEdge(gate, gateInput2, edgeId);
            }

        }

    }

    private void createStartEndVertexes() {
        wires.keySet().stream().filter(this::isStartOrEnd).forEach(this::createStartEndVertex);
    }


    private void initializeGates(List<String> inputs) {
        List<String> gatesInputs = inputs.stream().skip(inputs.indexOf(StringUtils.EMPTY)+1).toList();

        for(String input : gatesInputs) {
            Matcher matcher = GATE_PATTERN.matcher(input);
            if(matcher.find()) {
                String operation = matcher.group(2);
                String input1 = matcher.group(1);
                String input2 = matcher.group(3);
                String output = matcher.group(4);

                GateVisual gateVisual = new GateVisual(operation, input1, input2, output);
                gates.add(gateVisual);
                gatesPerOutput.put(output, gateVisual);
                graph.addVertex(gateVisual);
            }
        }
    }

    private void initializeWires(List<String> inputs) {

        boolean inputGates = false;
        for (String input : inputs) {

            if (StringUtils.EMPTY.equals(input)) {
                inputGates = true;
                continue;
            }

            if (!inputGates) {
                String[] wireInput = input.replace(StringUtils.SPACE, StringUtils.EMPTY).split(":");
                wires.put(wireInput[0], Integer.parseInt(wireInput[1]));
            } else {
                Matcher matcher = GATE_PATTERN.matcher(input);
                if (matcher.find()) {
                    wires.putIfAbsent(matcher.group(1), -1);
                    wires.putIfAbsent(matcher.group(3), -1);
                    wires.putIfAbsent(matcher.group(4), -1);
                }
            }
        }

        endWires = wires.keySet().stream().filter(w -> w.startsWith("z")).sorted().toList().reversed();
    }


    public String solveB() {

        // Obtain the graph in DOT format:
        paint();

        // Visual finding of invalid edges
        System.out.println("Tools:");
        System.out.println("- (Optional) graphviz: sudo apt install graphviz");
        System.out.println("- graphviz2drawio: pip install graphviz2drawio");
        System.out.println();
        System.out.println("Procedure:");
        System.out.println("1. Add the result to a file: dotgraph_day24.txt");
        System.out.println("2. (Optional) Execute command: cat day24.txt | dot -Tsvg > day24.svg");
        System.out.println("3. Execute command: graphviz2drawio dotgraph_day24.txt");
        System.out.println("4. Open the exported 'dotgraph_day24.xml' file at https://draw.io");
        System.out.println("5. Order the graph nodes until you see the invalid connections");

        // Invalid gates are:
        // bbp AND wwg -> z09
        // wwg XOR bbp -> hnd
        // ncj OR pwk -> z16
        // jkw XOR mqf -> tdv
        // x23 AND y23 -> z23
        // mfr XOR scw -> bks
        // x37 AND y37 -> tjp
        // x37 XOR y37 -> nrn

        // So the outputs are: z09,hnd,z16,tdv,z23,bks,tjp,nrn
        List<String> outputGates = List.of("z09","hnd","z16","tdv","z23","bks","tjp","nrn");

        return outputGates.stream().sorted().collect(Collectors.joining(","));
    }

    private void createStartEndVertex(String wire) {
        GateVisual gateVisual = new GateVisual(wire);
        graph.addVertex(gateVisual);

        if(isStart(wire)) {
            gatesPerOutput.put(wire, gateVisual);
        } else if(isEnd(wire)) {
            gates.add(gateVisual);
        }

    }

    private boolean isStart(String wire) {
        return wire.startsWith("x") || wire.startsWith("y");
    }

    private boolean isEnd(String wire) {
        return wire.startsWith("z");
    }

    private boolean isStartOrEnd(String wire) {
        return isStart(wire) || isEnd(wire);
    }

    private void paint() {
        DOTExporter<GateVisual, String> exporter = new DOTExporter<>(v -> v.getLabel());
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        System.out.println(writer.toString());
    }


}
