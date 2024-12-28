package com.adventofcode.flashk.day24;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CrossedWiresBF {

    private static final Pattern GATE_PATTERN = Pattern.compile("(\\w*) (AND|XOR|OR) (\\w*) -> (\\w*)");

    private final Map<String,Integer> wires = new HashMap<>();
    private final List<GateBF> gates = new ArrayList<>();
    private List<String> endWires;

    public CrossedWiresBF(List<String> inputs) {
        initializeWires(inputs);
        initializeGates(inputs);
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
                gates.add(new GateBF(operation, input1, input2, output));
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


    public long solveA() {

        // Brute force version for the algorithm:
        // Traverses the list attempting to calculate gate outputs until there are no gate changes.

        int i = 0;
        do {
            GateBF gate = gates.get(i++);

            if(!gate.isVisited()) {
                int input1 = wires.get(gate.getInput1());
                int input2 = wires.get(gate.getInput2());

                if (input1 != -1 && input2 != -1) {
                    wires.put(gate.getOutput(), gate.operate(input1, input2));
                    gate.setVisited(true);
                    i = 0;
                }
            }

        } while(i < gates.size());

        String binaryValue = endWires.stream().map(this::getBinaryValue).collect(Collectors.joining());
        return Long.valueOf(binaryValue, 2);
    }

    private String getBinaryValue(String wire) {
        return String.valueOf(wires.get(wire));
    }
}
