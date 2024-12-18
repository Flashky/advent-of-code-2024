package com.adventofcode.flashk.day18;

import com.adventofcode.flashk.common.Vector2;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class ByteCell implements Comparable<ByteCell> {

    private static final char CORRUPTED = '#';
    private static final char EMPTY = '.';

    private final Vector2 position;
    private char value = EMPTY;

    private long distance = Long.MAX_VALUE;
    private boolean visited = false;
    private ByteCell parent;

    public ByteCell(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public boolean isCorrupted() {
        return value == CORRUPTED;
    }

    public void corrupt() {
        value = CORRUPTED;
    }

    public void reset() {
        distance = Long.MAX_VALUE;
        visited = false;
        parent = null;
    }

    public boolean isReachable() {
        return distance != Long.MAX_VALUE;
    }

    public void paint() {
        System.out.print(value);
    }

    @Override
    public int compareTo(@NotNull ByteCell o) {
        return Long.compare(distance, o.distance);
    }
}
