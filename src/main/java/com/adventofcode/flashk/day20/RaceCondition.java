package com.adventofcode.flashk.day20;

import com.adventofcode.flashk.common.Vector2;
import com.adventofcode.flashk.day18.ByteCell;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class RaceCondition {

    private Set<Vector2> directions = Set.of(Vector2.right(), Vector2.left(), Vector2.up(), Vector2.down());

    private int rows;
    private int cols;

    private RaceTile[][] map;

    private int baseTime = -1; // So we don't count Start tile
    private RaceTile start;
    private RaceTile end;

    private Set<RaceTile> cheatableWalls = new HashSet<>();
    Map<Long, Long> numberOfCheatsPerSavedPs = new HashMap<>();

    public RaceCondition(char[][] inputs) {
        rows = inputs.length;
        cols = inputs[0].length;

        map = new RaceTile[rows][cols];

        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                RaceTile tile = new RaceTile(new Vector2(col, row), inputs[row][col]);
                if (tile.isEmpty()) {
                    baseTime++;
                }

                if (tile.isStart()) {
                    start = tile;
                } else if (tile.isEnd()) {
                    end = tile;
                }

                map[row][col] = tile;

            }
        }

        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(isCheatable(map[row][col])) {
                    cheatableWalls.add(map[row][col]);
                }
            }
}
    }

    private boolean isCheatable(RaceTile tile) {

        if(tile.isEmpty()) {
            return false;
        }

        Vector2 pos = tile.getPosition();

        // Cannot cheat on border tiles
        if (pos.getX() == 0 || pos.getX() == cols - 1 || pos.getY() == 0 || pos.getY() == rows - 1) {
            return false;
        }

        Vector2 left = Vector2.transform(pos, Vector2.left());
        Vector2 right = Vector2.transform(pos, Vector2.right());

        if (map[left.getY()][left.getX()].isEmpty() && map[right.getY()][right.getX()].isEmpty()) {
            return true;
        }

        Vector2 up = Vector2.transform(pos, Vector2.down());
        Vector2 down = Vector2.transform(pos, Vector2.up());

        return map[up.getY()][up.getX()].isEmpty() && map[down.getY()][down.getX()].isEmpty();
    }

    public void runRaces() {

        // Step 1 - Buscar todos los muros que SI se puedan saltar
        // Un muro se puede saltar en dos casos:
        // - Tiene tanto a izquierda como a derecha un espacio vacío.
        // - Tiene tanto arriba como abajo un espacio vacío.

        // Step 2 - Recorrer todos los muros que se puedan saltar:
        // - Se aplica dijkstra
        // - Se anota el coste del camino hasta la meta
        // - Se calcula el ahorro: ahorro = baseTime - coste end tile y se guarda en un mapa de resultados
        // - Se resetea el mapa entero

        for(RaceTile wall : cheatableWalls) {
            wall.cheat();
            dijkstra(start);
            long distance = end.getDistance();
            long savedTime = baseTime - distance;
            long savedTimeCount = numberOfCheatsPerSavedPs.getOrDefault(savedTime,0L);
            numberOfCheatsPerSavedPs.put(savedTime, savedTimeCount+1);
            reset();
        }

    }

    public long solveA(int savedPicoseconds) {

        return numberOfCheatsPerSavedPs.entrySet().stream()
                .filter(entry -> entry.getKey() >= savedPicoseconds)
                .mapToLong(entry -> entry.getValue())
                .sum();

    }



   private void dijkstra(RaceTile start) {
       start.setDistance(0);

       PriorityQueue<RaceTile> queue = new PriorityQueue<>();
       queue.add(start);

       while(!queue.isEmpty()) {
           RaceTile parent = queue.poll();
           parent.setVisited(true);
           Set<RaceTile> adjacents = getAdjacents(parent);

           for(RaceTile adjacent : adjacents) {
               if(adjacent.getDistance() > parent.getDistance()+1) {
                   adjacent.setDistance(parent.getDistance()+1);
                   adjacent.setParent(parent);
                   queue.add(adjacent);
               }
           }
       }
   }

    private Set<RaceTile> getAdjacents(RaceTile cell) {

        Set<RaceTile> adjacents = new HashSet<>();
        for(Vector2 dir : directions) {
            Vector2 newPos = Vector2.transform(cell.getPosition(), dir);

            if(isInbounds(newPos.getY(), newPos.getX())) {
                RaceTile newCell = map[newPos.getY()][newPos.getX()];

                if (!newCell.isWall() && !newCell.isVisited()) {
                    adjacents.add(newCell);
                }
            }
        }

        return adjacents;

    }

    private boolean isInbounds(int row, int col) {
        return row >= 0 &&  row < rows && col >= 0 && col < cols;
    }

    private void reset() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                map[row][col].reset();
            }
        }
    }

}
