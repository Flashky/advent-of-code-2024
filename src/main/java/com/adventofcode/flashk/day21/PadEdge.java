package com.adventofcode.flashk.day21;

public record PadEdge(String label, int id) {

    private static int idGenerator = 0;

    public PadEdge(Character label) {
        this(String.valueOf(label), idGenerator++);
    }

    public PadEdge(String label) {
        this(label, idGenerator++);
    }

}
