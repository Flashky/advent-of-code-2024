package com.adventofcode.flashk.day12;

import com.adventofcode.flashk.common.Vector2;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GardenPlot {

    private char plant;

    @Setter
    private int regionId = 0;
    private Vector2 position;

    @Setter
    private boolean visited = false;

    public GardenPlot(char plant, Vector2 position) {
        this.plant = plant;
        this.position = position;
    }

    public boolean hasRegion() {
        return regionId != 0;
    }
}
