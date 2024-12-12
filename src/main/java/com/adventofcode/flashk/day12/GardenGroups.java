package com.adventofcode.flashk.day12;

import com.adventofcode.flashk.common.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GardenGroups {

    private int rows;
    private int cols;
    private GardenPlot[][] map;
    private final Map<Integer, Integer> regionAreas = new HashMap<>();

    public GardenGroups(char[][] inputs) {

        rows = inputs.length;
        cols = inputs[0].length;
        map = new GardenPlot[rows][cols];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++) {
                map[row][col] = new GardenPlot(inputs[row][col], new Vector2(col, row));
            }
        }

    }

    public long solveA() {
        findRegions();
        return calculatePrice();
    }

    public long solveB() {
        findRegions();
        // TODO calculate sides of each region
        // then calculate price
        return 0L;
    }

    private void findRegions() {
        int regionId = 1;
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(!map[row][col].hasRegion()) {
                    int regionArea = findRegion(map[row][col], map[row][col].getPlant(), regionId);
                    regionAreas.put(regionId, regionArea);
                    regionId++;
                }

            }
        }
    }

    private int findRegion(GardenPlot gardenPlot, char plant, int regionId) {

        if(gardenPlot.isVisited()) {
            return 0;
        }

        if(gardenPlot.hasRegion()) {
            return 0;
        }

        if(gardenPlot.getPlant() != plant) {
            return 0;
        }

        int area = 1;
        gardenPlot.setVisited(true);
        gardenPlot.setRegionId(regionId);

        Set<GardenPlot> adjacentPlots = getAdjacentPlots(gardenPlot);

        for(GardenPlot adjacentPlot : adjacentPlots) {
            area += findRegion(adjacentPlot, plant, regionId);
        }

        gardenPlot.setVisited(false);

        return area;
    }

    private long calculatePrice() {

        long price = 0;

        for(int row = 0; row < rows; row++) {

            GardenPlot previousPlot = null;
            for(int col = 0; col < cols;col++) {
                GardenPlot plot = map[row][col];

                if(previousPlot == null) {
                    price += regionAreas.get(plot.getRegionId());
                } else if(previousPlot.getRegionId() != plot.getRegionId()) {
                    price += regionAreas.get(previousPlot.getRegionId());
                    price += regionAreas.get(plot.getRegionId());
                }

                previousPlot = plot;
            }

            // Add price of right-most fence
            price += regionAreas.get(previousPlot.getRegionId());

        }


        for(int col = 0; col < cols; col++) {

            GardenPlot previousPlot = null;
            for(int row = 0; row < rows; row++) {
                GardenPlot plot = map[row][col];

                if(previousPlot == null) {
                    price += regionAreas.get(plot.getRegionId());
                } else if(previousPlot.getRegionId() != plot.getRegionId()) {
                    price += regionAreas.get(previousPlot.getRegionId());
                    price += regionAreas.get(plot.getRegionId());
                }

                previousPlot = plot;
            }

            // Add price of right-most fence
            price += regionAreas.get(previousPlot.getRegionId());
        }

        return price;

    }

    private Set<GardenPlot> getAdjacentPlots(GardenPlot gardenPlot) {

        Set<GardenPlot> adjacentPlots = new HashSet<>();

        Vector2 initialPos = gardenPlot.getPosition();

        Vector2 nextPos = Vector2.transform(initialPos, Vector2.left());
        if(isInbounds(nextPos)) {
            adjacentPlots.add(map[nextPos.getY()][nextPos.getX()]);
        }

        nextPos = Vector2.transform(initialPos, Vector2.right());
        if(isInbounds(nextPos)) {
            adjacentPlots.add(map[nextPos.getY()][nextPos.getX()]);
        }

        nextPos = Vector2.transform(initialPos, Vector2.up());
        if(isInbounds(nextPos)) {
            adjacentPlots.add(map[nextPos.getY()][nextPos.getX()]);
        }

        nextPos = Vector2.transform(initialPos, Vector2.down());
        if(isInbounds(nextPos)) {
            adjacentPlots.add(map[nextPos.getY()][nextPos.getX()]);
        }

        return adjacentPlots;
    }

    private boolean isInbounds(Vector2 pos) {
        return (pos.getY() >= 0 && pos.getY() < rows && pos.getX() >= 0 && pos.getX() < cols);
    }
}
