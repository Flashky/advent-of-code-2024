package com.adventofcode.flashk.day07;

import java.util.List;

public class BridgeRepair {

    private final List<Equation> equations;

    public BridgeRepair(List<String> inputs) {
        equations = inputs.stream().map(Equation::new).toList();
    }

    public long solve(boolean concatenate) {
        return equations.stream().mapToLong(eq -> eq.solve(concatenate)).sum();
    }

}
