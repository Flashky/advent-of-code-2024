package com.adventofcode.flashk.day16;

import com.adventofcode.flashk.common.Vector2;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class Tile implements Comparable<Tile>{

    private static final char WALL = '#';
    private static final char START = 'S';
    private static final char END = 'E';

    private final Vector2 position;
    private final char value;

    // Dijkstra values
    private long score = Long.MAX_VALUE;
    private long partialScore;
    private boolean visited = false;
    private Vector2 direction;
    private Tile parent;

    // Reversed Dijkstra values
    private long scoreToEnd = Long.MAX_VALUE;
    private boolean visitedReverse = false;
    private Vector2 directionReverse;

    private boolean path = false;

    public Tile(Vector2 position, char value) {
        this.position = position;
        this.value = value;
    }

    public Vector2 getPosition() {
        return new Vector2(position);
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
