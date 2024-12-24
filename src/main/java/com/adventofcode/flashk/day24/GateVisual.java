package com.adventofcode.flashk.day24;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class GateVisual {

    private static int id = 0;

    private final String label;
    private final String operation;
    private String input1;
    private String input2;
    private String output;

    public GateVisual(String label) {
        this.label = label;
        this.operation = StringUtils.EMPTY;
        if(label.startsWith("x") || label.startsWith("y")) {
            this.output = label;
        } else if(label.startsWith("z")) {
            this.input1 = label;
        }
    }

    public GateVisual(String operation, String input1, String input2, String output) {
        this.label = operation + "_" + output;
        this.operation = operation;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public int operate(int value1, int value2) {
        return switch (operation) {
            case "AND" -> value1 & value2;
            case "XOR" -> value1 ^ value2;
            case "OR" -> value1 | value2;
            case null, default -> throw new IllegalStateException("Unknown gate operation: " + operation);
        };
    }

}
