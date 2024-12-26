package com.adventofcode.flashk.day24;

import java.util.UUID;

public class LogicalGate {

    private final String id = UUID.randomUUID().toString();
    private final String operation;

    public LogicalGate(String operation) {
        this.operation = operation;
    }

    public boolean canOperate(Wire input1, Wire input2) {
        return input1.getValue().isPresent() && input2.getValue().isPresent();
    }

    ///  Operates the gate using its inputs wires
    ///
    /// @return the output [Wire]()
    /// @throws IllegalStateException if any of the wires has no value present or if the gate operation is unknown.
    public int operate(Wire input1, Wire input2) {

        int value1 = input1.getValue().orElseThrow(IllegalStateException::new);
        int value2 = input2.getValue().orElseThrow(IllegalStateException::new);

        int result;

        switch (operation) {
            case "AND" -> result = value1 & value2;
            case "XOR" -> result = value1 ^ value2;
            case "OR" -> result = value1 | value2;
            case null, default -> throw new IllegalStateException("Unknown gate operation: " + operation);
        }

        return result;

    }

}
