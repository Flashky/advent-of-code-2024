package com.adventofcode.flashk.day17;

public record ProgramState(long number, long a, long b, long c, String out) implements Comparable<ProgramState> {

        @Override
        public int compareTo(ProgramState o) {
            return Long.compare(a, o.a);
        }
}
