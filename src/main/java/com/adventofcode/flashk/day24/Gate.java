package com.adventofcode.flashk.day24;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class Gate implements Comparable<Gate> {

    private String id = UUID.randomUUID().toString();
    private String operation;
    private Wire input1;
    private Wire input2;
    private Wire output;
    private WireEdge outputEdge;

    private boolean visited = false;

    public Gate(String operation, Wire input1, Wire input2, Wire output) {
        this.operation = operation;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.outputEdge = new WireEdge(output);
    }

    public boolean canOperate() {
        return input1.hasCurrent() && input2.hasCurrent();
    }

    ///  Operates the gate using its inputs wires
    ///
    /// @return the output [Wire]()
    /// @throws IllegalStateException if any of the wires has no value present or if the gate operation is unknown.
    public WireEdge operate() {

        int value1 = input1.getValue().orElseThrow(IllegalStateException::new);
        int value2 = input2.getValue().orElseThrow(IllegalStateException::new);

        switch (operation) {
            case "AND" -> output.setValue(value1 & value2);
            case "XOR" -> output.setValue(value1 ^ value2);
            case "OR" -> output.setValue(value1 | value2);
            case null, default -> throw new IllegalStateException("Unknown gate operation: " + operation);
        }

        return outputEdge;
    }


    @Override
    public int compareTo(@NotNull Gate o) {
        if(this.canOperate() && !o.canOperate()) {
            return -1;
        }

        if(!this.canOperate() && o.canOperate()) {
            return 1;
        }

        return 0;
    }

}
