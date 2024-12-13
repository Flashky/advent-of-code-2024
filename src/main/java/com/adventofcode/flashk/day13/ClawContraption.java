package com.adventofcode.flashk.day13;

import java.util.ArrayList;
import java.util.List;

public class ClawContraption {

    private List<Machine> machines = new ArrayList<>();

    public ClawContraption(List<String> inputs) {
        for(int i = 0; i < inputs.size(); i += 4) {
            machines.add(new Machine(inputs.get(i), inputs.get(i + 1), inputs.get(i + 2)));
        }
    }

    public long solveA() {
        return machines.stream().map(Machine::optimize).filter(m -> m != -1).reduce(0L, Long::sum);
    }

    public long solveB() {
        return machines.stream().map(Machine::optimizeB).filter(m -> m != -1).reduce(0L, Long::sum);
    }
}
