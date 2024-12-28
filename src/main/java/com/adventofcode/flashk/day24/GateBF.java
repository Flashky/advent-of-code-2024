package com.adventofcode.flashk.day24;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GateBF {

    private final String operation;
    private String input1;
    private String input2;
    private String output;

    @Setter
    private boolean visited = false;

    public GateBF(String operation, String input1, String input2, String output) {
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
