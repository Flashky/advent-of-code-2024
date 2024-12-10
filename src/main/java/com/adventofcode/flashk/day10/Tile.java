package com.adventofcode.flashk.day10;

import com.adventofcode.flashk.common.Vector2;
import lombok.Getter;
import lombok.Setter;

public class Tile {

    @Getter
    @Setter
    private boolean visited = false;
    @Getter
    private Vector2 position;
    @Getter
    private int height;

    public Tile(Vector2 position, int height) {
        this.position = position;
        this.height = height;
    }

}
