package com.adventofcode.flashk.day20;

import com.adventofcode.flashk.common.Vector2;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class RaceTile implements Comparable<RaceTile> {

    private static final char WALL = '#';
    private static final char EMPTY = '.';
    private static final char START = 'S';
    private static final char END = 'E';

    private Vector2 position;
    private long value;

    private boolean visited = false;
    private long distance = Long.MAX_VALUE;
    private long distanceToEnd = Long.MAX_VALUE;
    private boolean cheat = false;
    private RaceTile parent;

    public RaceTile(Vector2 position, char value) {
        this.position = position;
        this.value = value;
    }

    public void cheat() {
        value = EMPTY;
        cheat = true;
    }

    public void reset(){
        visited = false;
        distance = Long.MAX_VALUE;
        value = cheat ? WALL : value;
        cheat = false;
        parent = null;
    }

    public boolean isStart() {
        return value == START;
    }

    public boolean isEnd() {
        return value == END;
    }

    public boolean isEmpty() {
        return !isWall();
    }

    public boolean isWall() {
        return value == WALL;
    }

    public void paint() {
        System.out.print(value);
    }

    @Override
    public int compareTo(@NotNull RaceTile o) {
        return Long.compare(distance, o.distance);
    }
}
