package com.adventofcode.flashk.day10;

import com.adventofcode.flashk.common.Vector2;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Tile {

    @Setter
    private boolean visited = false;
    private final Vector2 position;
    private final int height;

    public Tile(Vector2 position, int height) {
        this.position = position;
        this.height = height;
    }

}
