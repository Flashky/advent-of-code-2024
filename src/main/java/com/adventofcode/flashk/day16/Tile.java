package com.adventofcode.flashk.day16;

import com.adventofcode.flashk.common.Vector2;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
public class Tile implements Comparable<Tile>{

    private static final char WALL = '#';
    private static final char START = 'S';
    private static final char END = 'E';

    private final Vector2 position;
    private final char value;

    @Setter
    private boolean visited = false;
    @Setter
    private long score = Long.MAX_VALUE;


    public Tile(Vector2 position, char value) {
        this.position = position;
        this.value = value;
    }

    public boolean isWall() {
        return value == WALL;
    }

    public boolean isStart(){
        return value == START;
    }

    public boolean isEnd() {
        return value == END;
    }

    @Override
    public int compareTo(@NotNull Tile o) {
        return Long.compare(this.score, o.score);
    }
}
