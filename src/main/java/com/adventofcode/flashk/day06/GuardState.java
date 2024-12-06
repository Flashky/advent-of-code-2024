package com.adventofcode.flashk.day06;

import com.adventofcode.flashk.common.Vector2;

public record GuardState(Vector2 pos, Vector2 dir) {

    public Vector2 pos() {
        return new Vector2(pos);
    }

    public Vector2 dir() {
        return new Vector2(dir);
    }
}
