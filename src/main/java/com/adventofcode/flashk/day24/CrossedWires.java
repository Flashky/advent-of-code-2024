package com.adventofcode.flashk.day24;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.graph.DirectedMultigraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CrossedWires {

    private static final Pattern GATE_PATTERN = Pattern.compile("(\\w*) (AND|XOR|OR) (\\w*) -> (\\w*)");

    private DirectedMultigraph<LogicalGate,WireEdge> graph = new DirectedMultigraph<>(WireEdge.class);

    Map<String, Wire> wires = new HashMap<>();
    private final List<LogicalGate> gates = new ArrayList<>();

    // Attributes that help start and end the algorithm
    private final List<LogicalGate> initialGates = new ArrayList<>();
    private final List<Wire> endWires = new ArrayList<>();

    public CrossedWires(List<String> inputs) {

        // Maps a wire with the gate that generates it.
        Map<Wire,LogicalGate> originGates = new HashMap<>();

        boolean inputWires = true;

        for (String input : inputs) {
            if (StringUtils.isBlank(input)) {
                inputWires = false;
                continue;
            }

            if (inputWires) {
                String[] wireInput = input.replace(StringUtils.SPACE, StringUtils.EMPTY).split(":");
                wires.put(wireInput[0], new Wire(wireInput[0], Integer.parseInt(wireInput[1])));
            } else {
                createGate(input, originGates);
            }
        }


        // Connect graph gates
        /*
        for(LogicalGate gate : gates) {

            // First input
            if(originGates.containsKey(gate.getInput1())) {
                Gate inputGate1 = originGates.get(gate.getInput1());
                graph.addEdge(inputGate1, gate, new WireEdge(gate.getInput1()));
            }

            // Second input
            if(originGates.containsKey(gate.getInput2())) {
                Gate inputGate2 = originGates.get(gate.getInput2());
                graph.addEdge(inputGate2, gate, new WireEdge(gate.getInput2()));
            }
        }
*/
    }

    private void createGate(String input, Map<Wire,LogicalGate> wireOriginGates) {
        Matcher matcher = GATE_PATTERN.matcher(input);
        if(matcher.find()) {

            // Create the gate vertex
            String operation = matcher.group(2);
            LogicalGate gate = new LogicalGate(operation);

            // Create the wires
            getOrCreateWire(matcher.group(1)); // Input wire 1
            getOrCreateWire(matcher.group(3)); // Input wire 2
            Wire outputWire = getOrCreateWire(matcher.group(4));


            // Add it to structures
            gates.add(gate);
            graph.addVertex(gate);

            /*
            if(gate.canOperate()) {
                initialGates.add(gate);
            }*/

            // Wire management for building the graph
            if(outputWire.getLabel().startsWith("z")) {
                endWires.add(outputWire);
            }

            wireOriginGates.put(outputWire, gate);

        }
    }

    private void buildGraph() {
        LogicalGate start = new StartGate("OR");

    }
    /// Retrieves a [Wire]() associated to the label.
    ///
    /// If the label does not exist, it creates a new wire and returns it.
    /// @param label the wire label
    /// @return a [Wire]()
    private Wire getOrCreateWire(String label) {
        Wire wire = wires.getOrDefault(label, new Wire(label));
        wires.putIfAbsent(label, wire);
        return wire;
    }

    public long solveA() {

        /*
        PriorityQueue<Gate> gates = new PriorityQueue<>();
        gates.addAll(initialGates);

        while(!gates.isEmpty()) {

            // Extract first those gates which can operate
            Gate gate = gates.poll();
            gate.setVisited(true);

            // Operate it
            WireEdge outputEdge = gate.operate();
            Wire output = outputEdge.wire();
            System.out.println("Wire "+output.getLabel()+ " has value: "+output.getValue().get());

            // Calculate the next gate that should be added to the queue.
            if(!output.isEnd()) {
                Gate outputGate = graph.getEdgeTarget(outputEdge);
                if(!outputGate.isVisited()) {
                    gates.add(outputGate);
                }
            }

        }*/
        
        String binaryValue = endWires.stream().sorted().map(Wire::getBinaryValue).collect(Collectors.joining());
        return Long.valueOf(binaryValue, 2);
    }
    
    private Set<Gate> getAdjacents(Gate gate) {
        // TODO cálculo de adyacentes
        return new HashSet<>();
    }
}
