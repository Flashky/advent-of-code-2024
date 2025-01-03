package com.adventofcode.flashk.day12;

import com.adventofcode.flashk.common.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GardenGroups {

    private final int rows;
    private final int cols;
    private final GardenPlot[][] map;
    private final Map<Integer, Integer> regionAreas = new HashMap<>();
    private final Map<Integer, Integer> regionSides = new HashMap<>();
    private final Map<Integer, Set<GardenPlot>> regionPlots = new HashMap<>();
    private GardenPlot noPlot;

    public GardenGroups(char[][] inputs) {

        rows = inputs.length;
        cols = inputs[0].length;
        map = new GardenPlot[rows][cols];

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++) {
                map[row][col] = new GardenPlot(inputs[row][col], new Vector2(col, row));
            }
        }

        noPlot = new GardenPlot('.', new Vector2(-1,-1));
        noPlot.setRegionId(-1);

    }

    public long solveA() {
        findRegions();
        return calculatePrice();
    }

    public long solveB() {
        findRegions();
        countHorizontalSides();
        countVerticalSides();
        return calculatePriceB();
    }

    private int calculatePriceB() {
        int price = 0;
        for(Map.Entry<Integer,Integer> regionArea : regionAreas.entrySet()) {
            price += regionArea.getValue() * regionSides.get(regionArea.getKey());
        }

        return price;
    }

    private void countHorizontalSides() {

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {

                GardenPlot currentPlot = map[row][col];
                GardenPlot leftPlot = getLeftPlot(row, col).orElse(noPlot());

                // Search horizontal sides up
                GardenPlot topPlot =  getUpPlot(row, col).orElse(noPlot());
                GardenPlot topLeftPlot = getUpLeftPlot(row,col).orElse(noPlot());

                if(checkAdjacents(leftPlot, topPlot, currentPlot) ||
                        checkAdjacentsAndDiagonalH(leftPlot, topPlot, topLeftPlot, currentPlot)) {
                    addSides(currentPlot);
                }

                // Search horizontal sides down
                GardenPlot downPlot = getDownPlot(row,col).orElse(noPlot());
                GardenPlot downLeftPlot = getDownLeftPlot(row, col).orElse(noPlot);

                if(checkAdjacents(leftPlot, downPlot, currentPlot) ||
                        checkAdjacentsAndDiagonalH(leftPlot, downPlot, downLeftPlot, currentPlot))  {
                    addSides(currentPlot);
                }
            }
        }
    }

    private boolean checkAdjacents(GardenPlot adjacentHorizontal, GardenPlot adjacentVertical, GardenPlot current) {

        // Cases:
        // XX    AX
        // XA<   XA<

        // XA<  XA<
        // XX   AX

        return (adjacentHorizontal.getRegionId() != current.getRegionId() &&
                adjacentVertical.getRegionId() != current.getRegionId());
    }

    private boolean checkAdjacentsAndDiagonalH(GardenPlot adjacentHorizontal, GardenPlot adjacentVertical,
                                               GardenPlot diagonal, GardenPlot current) {

        // Cases:

        // AX
        //  -
        // AA <

        // AA<
        //  -
        // AX

        return (adjacentHorizontal.getRegionId() == current.getRegionId() &&
                adjacentVertical.getRegionId() != current.getRegionId() &&
                diagonal.getRegionId() == current.getRegionId());
    }

    private boolean checkAdjacentsAndDiagonalV(GardenPlot adjacentVertical, GardenPlot adjacentHorizontal,
                                               GardenPlot diagonal, GardenPlot current) {
        // Cases:

        // A A
        // X|A <

        // A A <
        // X|A

        return adjacentVertical.getRegionId() == current.getRegionId() &&
                adjacentHorizontal.getRegionId() != current.getRegionId() &&
                diagonal.getRegionId() == current.getRegionId();

    }

    private void addSides(GardenPlot gardenPlot) {
        int sides = regionSides.getOrDefault(gardenPlot.getRegionId(),0);
        regionSides.put(gardenPlot.getRegionId(), sides+1);
    }

    private GardenPlot noPlot() {
        return noPlot;
    }

    private Optional<GardenPlot> getLeftPlot(int row, int col) {
        return (col > 0) ? Optional.of(map[row][col-1]) : Optional.empty();
    }

    private Optional<GardenPlot> getRightPlot(int row, int col) {
        return (col+1 < cols) ? Optional.of(map[row][col+1]) : Optional.empty();
    }

    private Optional<GardenPlot> getUpPlot(int row, int col) {
        return (row > 0) ? Optional.of(map[row-1][col]) : Optional.empty();
    }

    private Optional<GardenPlot> getDownPlot(int row, int col) {
        return (row+1 < rows) ? Optional.of(map[row+1][col]) : Optional.empty();
    }

    private Optional<GardenPlot> getUpLeftPlot(int row, int col) {
        return (row > 0 && col > 0) ? Optional.of(map[row-1][col-1]) : Optional.empty();
    }

    private Optional<GardenPlot> getUpRightPlot(int row, int col) {
        return (row > 0 && col+1 < cols) ? Optional.of(map[row-1][col+1]) : Optional.empty();
    }

    private Optional<GardenPlot> getDownLeftPlot(int row, int col) {
        return (row+1 < rows && col > 0) ? Optional.of(map[row+1][col-1]) : Optional.empty();
    }

    private void countVerticalSides() {

        for(int col = 0; col < cols; col++) {
            for(int row = 0; row < rows; row++) {

                GardenPlot currentPlot = map[row][col];
                GardenPlot topPlot =  getUpPlot(row, col).orElse(noPlot());

                // Search vertical sides right
                GardenPlot rightPlot = getRightPlot(row, col).orElse(noPlot());
                GardenPlot topRightPlot = getUpRightPlot(row,col).orElse(noPlot());

                if(checkAdjacents(rightPlot, topPlot, currentPlot)
                        || checkAdjacentsAndDiagonalV(topPlot, rightPlot, topRightPlot, currentPlot)) {
                    addSides(currentPlot);
                }

                // Search vertical sides left
                GardenPlot leftPlot = getLeftPlot(row,col).orElse(noPlot());
                GardenPlot topLeftPlot = getUpLeftPlot(row, col).orElse(noPlot);

                if(checkAdjacents(leftPlot, topPlot, currentPlot) ||
                        checkAdjacentsAndDiagonalV(topPlot, leftPlot, topLeftPlot, currentPlot))  {
                    addSides(currentPlot);
                }
            }
        }
    }


    private void findRegions() {
        int regionId = 1;
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(!map[row][col].hasRegion()) {
                    regionPlots.put(regionId, new HashSet<>());
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
        regionPlots.get(regionId).add(gardenPlot);

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
