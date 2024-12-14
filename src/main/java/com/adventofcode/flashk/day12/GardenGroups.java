package com.adventofcode.flashk.day12;

import com.adventofcode.flashk.common.Vector2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GardenGroups {

    private final int rows;
    private final int cols;
    private final GardenPlot[][] map;
    private final Map<Integer, Integer> regionAreas = new HashMap<>();
    private final Map<Integer, Integer> regionSides = new HashMap<>();
    private final Map<Integer, Set<GardenPlot>> regionPlots = new HashMap<>();

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

        long price = 0;

        // Nueva idea:
        // Esta idea se parece más a como se calculan las vallas en la primera fila.
        // ¿cuál es la diferencia?
        //
        // supongamos una fila como ABAB, esto son 4 regiones diferentes, pero esto es lo de menos.
        //
        // En vez de centrarnos en una región para calcular su precio, recorreremos el array por filas:
        // - Nos situamos en una celda del mapa.
        // - Si esta celda NO pertenece a la misma región que a la anterior, le decimos a su región que tiene un lado más.
        // - Si esta celda pertenece a la misma región que la celda anterior, entonces podemos obviarla.
        //
        // Una vez terminado el array por filas, hacemos el mismo proceso por columnas, para así contar los otros lados.

        // Posibles casos eje: la primera celda y la última de cada fila/columna, ¿cuántos lados hay que contar?

        countHorizontalSides(); // TODO descomentar
        countVerticalSides();
        return price;
    }

    private void countHorizontalSides() {
        GardenPlot[] previousRow = null;
       
        for(int row = 0; row < rows; row++) {
            GardenPlot leftPlot = null;
            for(int col = 0; col < cols; col++) {

                GardenPlot plot = map[row][col];

                if(leftPlot != null && leftPlot.getRegionId() == plot.getRegionId()) {
                    // Left is same region
                    continue;
                }

                GardenPlot topPlot = previousRow != null ? previousRow[col] : null;

                if(topPlot != null && topPlot.getRegionId() == plot.getRegionId()) {
                    // Top is same region
                    continue;
                }

                int sides = regionSides.getOrDefault(plot.getRegionId(),0);
                regionSides.put(plot.getRegionId(), sides+1);

                leftPlot = plot;
            }
            previousRow = map[row];
        }
    }


    private void countVerticalSides() {

        GardenPlot[] previousCol = null;

        for(int col = 0; col < cols; col++) {
            GardenPlot topPlot = null;
            GardenPlot[] currentCol = new GardenPlot[cols];

            for(int row = 0; row < rows; row++) {

                GardenPlot plot = map[row][col];
                currentCol[row] = plot;

                if(topPlot != null && topPlot.getRegionId() == plot.getRegionId()) {
                    // Top is same region
                    continue;
                }

                GardenPlot leftPlot = previousCol != null ? previousCol[row] : null;

                if(leftPlot != null && leftPlot.getRegionId() == plot.getRegionId()) {
                    // Top is same region
                    continue;
                }

                int sides = regionSides.getOrDefault(plot.getRegionId(),0);
                regionSides.put(plot.getRegionId(), sides+1);

                topPlot = plot;
            }
            previousCol = currentCol;
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
