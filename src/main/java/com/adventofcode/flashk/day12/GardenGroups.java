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
        // Para cada región, calculamos cuantos lados tiene
        System.out.println("Region | Sides");
        for(Set<GardenPlot> gardenPlots : regionPlots.values()) {
            int sides = calculateRegionSides(gardenPlots);
            regionSides.put(gardenPlots.stream().findFirst().get().getRegionId(), sides);
            //price += regionAreas.get(gardenPlots.stream().findFirst().get().getRegionId()) * sides;
            System.out.println(gardenPlots.stream().findFirst().get().getPlant()+"     | "+sides);
        }

        // TODO calculate sides of each region
        // then calculate price

        for(Integer regionId : regionAreas.keySet()) {
            price += regionAreas.get(regionId) * regionSides.get(regionId);
        }

        return price;
    }

    private int calculateRegionSides(Set<GardenPlot> gardenPlots) {
        // Número de lados en un polígono irregular es:
        // nº lados = (suma de los ángulos interiores / 180) + 2
        // Por ejemplo, un polígono con forma de "L" tiene 5 ángulos internos de 90º y uno más de 270º, lo que suma 720º.
        // Por lo tanto, el número de lados es (720 / 180) + 2 = 6.

        // Problema: encontrar todos los ángulos interiores de cada región.

        // Cualquier plot del polígono puede tener estos casos:
        // - No tiene ángulos
        // - Tiene ángulos

        // Para determinar si hay ángulos, examinamos para cada región sus plots.
        //
        // Para cada plot hay que mirar los plots adyacentes:
        // - Si los dos adyacentes son de la misma región pero el diagonal es de otra región, entonces hay un ángulo
        //   interior de 270 en dicha esquina.
        // - Si los dos adyacentes son de otra región, independientemente de la diagonal, hay un ángulo interior de 90º
        // - En el resto de casos, no hay ángulos.

        int angles = 0;
        int externalAngles = 0;
        int internalAngles = 0;
        int internalConvexAngles = 0;

        for(GardenPlot gardenPlot : gardenPlots) {
            int currentExternalAngles = sumExternalAngles(gardenPlot);
            int currentInternalAngles = sumInternalAngles(gardenPlot); // Esto debería dar 1080 para el plot (2,2) ?
            //if(currentExternalAngles != 360 && currentInternalAngles != 1080) {
                externalAngles += currentExternalAngles;
                internalAngles += currentInternalAngles;
                System.out.print(gardenPlot.getPosition().getY() + ","+gardenPlot.getPosition().getX());
                System.out.println(" - EXT: "+currentExternalAngles + " - INT: "+ currentInternalAngles);
            //}


            internalConvexAngles += sumInternalConvexAngles(gardenPlot);
        }

        //angles = externalAngles + internalAngles + internalConvexAngles;
        angles = externalAngles + internalAngles + internalConvexAngles;
        // https://www.sciencing.com/how-to-find-the-number-of-sides-of-a-polygon-12751688/
        return (angles / 180) + 2;
    }

    private int sumInternalAngles(GardenPlot gardenPlot) {
        int angles = 0;

        if(hasUpLeftInternalAngle(gardenPlot)) {
            angles += 90; // 270
        }

        if(hasUpRightInternalAngle(gardenPlot)) {
            angles += 90;
        }

        if(hasDownLeftInternalAngle(gardenPlot)) {
            angles += 90;
        }

        if(hasDownRightInternalAngle(gardenPlot)) {
            angles += 90;
        }

        return angles;

    }

    private boolean hasUpLeftInternalAngle(GardenPlot gardenPlot) {

        // Get adjacent up and left
        Vector2 upPos = Vector2.transform(gardenPlot.getPosition(), Vector2.down());
        Vector2 leftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.left());

        // Garden plot is at top or left corner of the map
        if(!isInbounds(leftPos)) {
            return false;
        }

        if(!isInbounds(upPos)) {
            return false;
        }

        Vector2 upLeftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.downLeft());

        // If diagonal is the same region, there is no internal angle
        if(map[upLeftPos.getY()][upLeftPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        // Garden plot is at any other position of the map
        return (map[leftPos.getY()][leftPos.getX()].getRegionId() == gardenPlot.getRegionId() &&
                map[upPos.getY()][upPos.getX()].getRegionId() == gardenPlot.getRegionId());

    }

    private boolean hasUpRightInternalAngle(GardenPlot gardenPlot) {

        // Get adjacent up and right
        Vector2 upPos = Vector2.transform(gardenPlot.getPosition(), Vector2.down());
        Vector2 rightPos = Vector2.transform(gardenPlot.getPosition(), Vector2.right());

        // Garden plot is at top or right corner of the map
        if(!isInbounds(rightPos)) {
            return false;
        }

        if(!isInbounds(upPos)) {
            return false;
        }

        Vector2 upRightPos = Vector2.transform(gardenPlot.getPosition(), Vector2.downRight());

        // If diagonal is the same region, there is no internal angle
        if(map[upRightPos.getY()][upRightPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        // Garden plot is at any other position of the map
        return (map[rightPos.getY()][rightPos.getX()].getRegionId() == gardenPlot.getRegionId() &&
                map[upPos.getY()][upPos.getX()].getRegionId() == gardenPlot.getRegionId());

    }

    private boolean hasDownLeftInternalAngle(GardenPlot gardenPlot) {
        // Get adjacent down and left
        Vector2 downPos = Vector2.transform(gardenPlot.getPosition(), Vector2.up());
        Vector2 leftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.left());

        // Garden plot is at bottom or left corner of the map
        if(!isInbounds(leftPos)) {
            return false;
        }

        if(!isInbounds(downPos)) {
            return false;
        }

        Vector2 downLeftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.upLeft());

        // If diagonal is the same region, there is no internal angle
        if(map[downLeftPos.getY()][downLeftPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        // Garden plot is at any other position of the map
        return (map[leftPos.getY()][leftPos.getX()].getRegionId() == gardenPlot.getRegionId() &&
                map[downPos.getY()][downPos.getX()].getRegionId() == gardenPlot.getRegionId());
    }

    private boolean hasDownRightInternalAngle(GardenPlot gardenPlot) {
        // Get adjacent down and right
        Vector2 downPos = Vector2.transform(gardenPlot.getPosition(), Vector2.up());
        Vector2 rightPos = Vector2.transform(gardenPlot.getPosition(), Vector2.right());

        // Garden plot is at bottom or right corner of the map
        if(!isInbounds(rightPos)) {
            return false;
        }

        if(!isInbounds(downPos)) {
            return false;
        }

        Vector2 upLeftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.upRight());

        // If diagonal is the same region, there is no internal angle
        if(map[upLeftPos.getY()][upLeftPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        // Garden plot is at any other position of the map
        return (map[rightPos.getY()][rightPos.getX()].getRegionId() == gardenPlot.getRegionId() &&
                map[downPos.getY()][downPos.getX()].getRegionId() == gardenPlot.getRegionId());
    }

    private int sumInternalConvexAngles(GardenPlot gardenPlot) {
        int angles = 0;

        if(hasUpLeftInternalConvexAngle(gardenPlot)) {
            angles -= 90;
        }

        if(hasUpRightInternalConvexAngle(gardenPlot)) {
            angles -= 90;
        }

        if(hasDownLeftInternalConvexAngle(gardenPlot)) {
            angles -= 90;
        }

        if(hasDownRightInternalConvexAngle(gardenPlot)) {
            angles -= 90;
        }

        return angles;

    }

    private boolean hasUpLeftInternalConvexAngle(GardenPlot gardenPlot) {

        // Solo puede haber ángulos convexos en el interior del mapa
        if(isInBorder(gardenPlot.getPosition())) {
            return false;
        }

        // Get adjacent up and left
        Vector2 upPos = Vector2.transform(gardenPlot.getPosition(), Vector2.down());
        if(map[upPos.getY()][upPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        Vector2 leftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.left());
        if(map[leftPos.getY()][leftPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        Vector2 diagonalPos = Vector2.transform(gardenPlot.getPosition(), Vector2.downLeft());
        return map[diagonalPos.getY()][diagonalPos.getX()].getRegionId() == gardenPlot.getRegionId();
    }

    private boolean hasUpRightInternalConvexAngle(GardenPlot gardenPlot) {

        // Solo puede haber ángulos convexos en el interior del mapa
        if(isInBorder(gardenPlot.getPosition())) {
            return false;
        }

        // Get adjacent up and left
        Vector2 upPos = Vector2.transform(gardenPlot.getPosition(), Vector2.down());
        if(map[upPos.getY()][upPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        Vector2 rightPos = Vector2.transform(gardenPlot.getPosition(), Vector2.right());
        if(map[rightPos.getY()][rightPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        Vector2 diagonalPos = Vector2.transform(gardenPlot.getPosition(), Vector2.downRight());
        return map[diagonalPos.getY()][diagonalPos.getX()].getRegionId() == gardenPlot.getRegionId();
    }

    private boolean hasDownLeftInternalConvexAngle(GardenPlot gardenPlot) {

        // Solo puede haber ángulos convexos en el interior del mapa
        if(isInBorder(gardenPlot.getPosition())) {
            return false;
        }

        // Get adjacent up and left
        Vector2 downPos = Vector2.transform(gardenPlot.getPosition(), Vector2.up());
        if(map[downPos.getY()][downPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        Vector2 leftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.left());
        if(map[leftPos.getY()][leftPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        Vector2 diagonalPos = Vector2.transform(gardenPlot.getPosition(), Vector2.upLeft());
        return map[diagonalPos.getY()][diagonalPos.getX()].getRegionId() == gardenPlot.getRegionId();
    }

    private boolean hasDownRightInternalConvexAngle(GardenPlot gardenPlot) {

        // Solo puede haber ángulos convexos en el interior del mapa
        if(isInBorder(gardenPlot.getPosition())) {
            return false;
        }

        // Get adjacent up and left
        Vector2 downPos = Vector2.transform(gardenPlot.getPosition(), Vector2.up());
        if(map[downPos.getY()][downPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        Vector2 rightPos = Vector2.transform(gardenPlot.getPosition(), Vector2.right());
        if(map[rightPos.getY()][rightPos.getX()].getRegionId() == gardenPlot.getRegionId()) {
            return false;
        }

        Vector2 diagonalPos = Vector2.transform(gardenPlot.getPosition(), Vector2.upRight());
        return map[diagonalPos.getY()][diagonalPos.getX()].getRegionId() == gardenPlot.getRegionId();
    }

    private boolean isInBorder(Vector2 pos) {
        return (pos.getY() == 0 || pos.getY() == rows - 1 || pos.getX() == 0 || pos.getX() == cols - 1);
    }

    private int sumExternalAngles(GardenPlot gardenPlot) {

        int angles = 0;

        if(hasUpLeftExternalAngle(gardenPlot)) {
            angles += 90;
        }

        if(hasUpRightExternalAngle(gardenPlot)) {
            angles += 90;
        }

        if(hasDownLeftExternalAngle(gardenPlot)) {
            angles += 90;
        }

        if(hasDownRightExternalAngle(gardenPlot)) {
            angles += 90;
        }

        return angles;
    }

    private boolean hasUpLeftExternalAngle(GardenPlot gardenPlot) {

        // Get adjacent up and left
        Vector2 upPos = Vector2.transform(gardenPlot.getPosition(), Vector2.down());
        Vector2 leftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.left());

        // Garden plot is at top-left corner of the map
        if(!isInbounds(upPos) && !isInbounds(leftPos)) {
            return true;
        }

        // Garden plot is at left side of the map
        if(!isInbounds(leftPos) && isInbounds(upPos) &&
                map[upPos.getY()][upPos.getX()].getRegionId() != gardenPlot.getRegionId()) {
            return true;
        }

        // Garden plot is at top side of the map
        if((!isInbounds(upPos) && isInbounds(leftPos)) &&
                map[leftPos.getY()][leftPos.getX()].getRegionId() != gardenPlot.getRegionId()) {
            return true;
        }

        Vector2 diagonalPos = Vector2.transform(gardenPlot.getPosition(), Vector2.downLeft());

        return (isInbounds(upPos) &&
                isInbounds(leftPos) &&
                isInbounds(diagonalPos) &&
                map[upPos.getY()][upPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                map[leftPos.getY()][leftPos.getX()].getRegionId() != gardenPlot.getRegionId()) &&
                map[diagonalPos.getY()][diagonalPos.getX()].getRegionId() != gardenPlot.getRegionId();

    }

    private boolean hasUpRightExternalAngle(GardenPlot gardenPlot) {

        // Get adjacent up and right
        Vector2 upPos = Vector2.transform(gardenPlot.getPosition(), Vector2.down());
        Vector2 rightPos = Vector2.transform(gardenPlot.getPosition(), Vector2.right());

        // Garden plot is at a corner of the map
        if(!isInbounds(upPos) && !isInbounds(rightPos)) {
            return true;
        }

        // Garden plot is at the right side of the map
        if(isInbounds(upPos) && !isInbounds(rightPos) &&
                map[upPos.getY()][upPos.getX()].getRegionId() != gardenPlot.getRegionId()) {
            return true;
        }

        // Garden plot is at top side of the map
        if((!isInbounds(upPos) && isInbounds(rightPos)) &&
                map[rightPos.getY()][rightPos.getX()].getRegionId() != gardenPlot.getRegionId()) {
            return true;
        }

        Vector2 diagonalPos = Vector2.transform(gardenPlot.getPosition(), Vector2.downRight());

        // Garden plot is at any other position of the map
        return (isInbounds(rightPos) &&
                isInbounds(upPos) &&
                map[rightPos.getY()][rightPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                map[upPos.getY()][upPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                map[diagonalPos.getY()][diagonalPos.getX()].getRegionId() != gardenPlot.getRegionId());

    }

    private boolean hasDownLeftExternalAngle(GardenPlot gardenPlot) {

        // Get adjacent up and left
        Vector2 downPos = Vector2.transform(gardenPlot.getPosition(), Vector2.up());
        Vector2 leftPos = Vector2.transform(gardenPlot.getPosition(), Vector2.left());

        // Garden plot is at bottom-left corner of the map
        if(!isInbounds(downPos) && !isInbounds(leftPos)) {
            return true;
        }

        // Garden plot is at left side of the map
        if(isInbounds(downPos) && map[downPos.getY()][downPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                (!isInbounds(leftPos))) {
            return true;
        }

        // Garden plot is at bottom side of the map
        if(isInbounds(leftPos) && map[leftPos.getY()][leftPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                (!isInbounds(downPos))) {
            return true;
        }

        Vector2 diagonalPos = Vector2.transform(gardenPlot.getPosition(), Vector2.upLeft());

        // Garden plot is at any other position of the map
        return (isInbounds(leftPos) &&
                isInbounds(downPos)&&
                map[leftPos.getY()][leftPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                map[downPos.getY()][downPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                map[diagonalPos.getY()][diagonalPos.getX()].getRegionId() != gardenPlot.getRegionId());

    }

    private boolean hasDownRightExternalAngle(GardenPlot gardenPlot) {

        // Get adjacent down and right
        Vector2 downPos = Vector2.transform(gardenPlot.getPosition(), Vector2.up());
        Vector2 rightPos = Vector2.transform(gardenPlot.getPosition(), Vector2.right());

        // Garden plot is at bottom-right corner of the map
        if(!isInbounds(downPos) && !isInbounds(rightPos)) {
            return true;
        }

        // Garden plot is at right side of the map
        if(isInbounds(downPos) && map[downPos.getY()][downPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                (!isInbounds(rightPos))) {
            return true;
        }

        // Garden plot is at bottom side of the map
        if(isInbounds(rightPos) && map[rightPos.getY()][rightPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                (!isInbounds(downPos))) {
            return true;
        }

        Vector2 diagonalPos = Vector2.transform(gardenPlot.getPosition(), Vector2.upRight());

        // Garden plot is at any other position of the map
        return (isInbounds(rightPos) &&
                isInbounds(downPos) &&
                map[rightPos.getY()][rightPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                map[downPos.getY()][downPos.getX()].getRegionId() != gardenPlot.getRegionId() &&
                map[diagonalPos.getY()][diagonalPos.getX()].getRegionId() != gardenPlot.getRegionId());

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
