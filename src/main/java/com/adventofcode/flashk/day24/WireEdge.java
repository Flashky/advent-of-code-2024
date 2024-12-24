package com.adventofcode.flashk.day24;

import java.util.UUID;

public record WireEdge(String id, Wire wire) {

    public WireEdge(Wire wire) {
        this(UUID.randomUUID().toString(), wire);
    }
}
