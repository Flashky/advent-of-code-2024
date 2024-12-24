package com.adventofcode.flashk.day24;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Getter
@EqualsAndHashCode
public class Wire implements Comparable<Wire>{

    private final String label;

    @Setter
    private int value;

    public Wire(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public Wire(String label) {
        this.label = label;
        this.value = -1;
    }

    public String getBinaryValue() {
        if(value == -1) {
            throw new IllegalStateException("Cannot convert to binary a non set value");
        }
        return String.valueOf(value);
    }

    public Optional<Integer> getValue() {
        if(value == -1) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    public boolean hasCurrent() {
        return value != -1;
    }

    public boolean isStart() {
        return label.startsWith("x") || label.startsWith("y");
    }
    public boolean isEnd() {
        return label.startsWith("z");
    }

    @Override
    public int compareTo(@NotNull Wire o) {
        return -1 * this.label.compareTo(o.label);
    }
}
