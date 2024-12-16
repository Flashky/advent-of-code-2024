package com.adventofcode.flashk.day16;

import com.adventofcode.flashk.common.Vector2;

public class Movement {

    private Tile tile;
    private Vector2 direction;
    private boolean rotate;

    public Vector2 getDirection() {
        if(rotate) {
            return new Vector2(0,0);
        }
        return new Vector2(direction);
    }


}
