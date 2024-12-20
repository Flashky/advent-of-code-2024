package com.adventofcode.flashk.day20;

import com.adventofcode.flashk.common.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class RaceCondition {

    private final Set<Vector2> directions = Set.of(Vector2.right(), Vector2.left(), Vector2.up(), Vector2.down());

    private final int rows;
    private final int cols;
    private final RaceTile[][] map;

    private RaceTile start;
    private RaceTile end;

    private final List<RaceTile> pathTiles = new ArrayList<>();
    private final Map<Long, Long> numberOfCheatsPerSavedPs = new HashMap<>();

    // Idea
    // La distancia total desde el inicio S hasta el final E, pasando por un punto A en el que se aplica
    // cheat hasta el punto B es igual a la suma de las distancias:
    // distance = d(S, A) + d(A, B) + d(B, E)

    // Donde:
    // d(S, A) Se puede calcular de varias maneras:
    // - Con dijkstra
    // - Haciendo un simple recorrido que vaya desde S hasta E, inicializando cada celda con step+1

    // d(A, B) se puede calcular como la distancia Manhattan entre A y B
    // - Por ejemplo, en el caso más básico, si una celda está al lado de otra (1,0), (2,0), entonces la distancia es 1.
    // - Si la celda está a dos de pasos de distancia, entonces la distancia es 2.
    // - Y así sucesivamente.

    // d(B, E), para este cálculo podemos tener en cuenta lo siguiente:
    // d(B, E) = d(S, E) - d(S, B)
    // Por lo tanto, en el primer algoritmo podemos calcular para cada celda dos distancias:
    // - La distancia desde el inicio: d(S,X)
    // - La distancia restante hasta el final: d(E,S) = d(S,E) - d(S,A)

    public RaceCondition(char[][] inputs) {
        rows = inputs.length;
        cols = inputs[0].length;

        map = new RaceTile[rows][cols];
        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                RaceTile tile = new RaceTile(new Vector2(col, row), inputs[row][col]);
                if (tile.isEmpty() && !tile.isEnd()) {
                    pathTiles.add(tile);
                }

                if (tile.isStart()) {
                    start = tile;
                } else if (tile.isEnd()) {
                    end = tile;
                }

                map[row][col] = tile;
            }
        }

        // Apply dijkstra to obtain distance to any tile from the start
        // This allows to obtain distance from start node (S) to any node (X): d(S,X)
        // and also allows to calculate distance from start node (S) to end node (E): d(S,E)
        dijkstra(start);

        // Now add to every node (X) the relative distance from that node to the end node (E):
        // d(X,E) = d(S,E) - d(S,X)
        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                RaceTile tile = map[row][col];
                if(!tile.isWall()) {
                    tile.setDistanceToEnd(end.getDistance() - tile.getDistance());
                }
            }
        }

        // The idea of the algorithm for both part 1 and 2 is that total distance when cheating on any node will be:
        // distance = d(S, A) + d(A, B) + d(B, E)
        //
        // Where:
        // d(S, A) is the distance from start node to the node where cheating starts applying.
        // d(A, B) is the distance from the node where cheating starts applying to the node where it stops applying.
        // d(B, E) is the distance from the node where cheating stops applying to the end node.

        // d(S,A) is already calculated by dijkstra
        // d(B,E) is calculated after doing dijkstra
        // d(A,B) is calculated by the manhattan distance between two adjacent nodes.
        // Summing all these distances will give the total distance from start to end node when cheating from node A to B.

        // The previous formula works even if the cheats starts on S and ends on E, because in that case:
        // d(S, A) = d(S, S) = 0
        // d(A, B) = X
        // d(B, E) = 0
        // So the total distance will from S to E will be X.
        // Keep in mind that distance will be lower than the normal d(S, E) distance.
    }

    public void runRaces(int cheatTime) {

        if(cheatTime < 2) {
            throw new IllegalArgumentException("Cheat time must be at least 2");
        }

        Set<Vector2> movements = movements(cheatTime);

        for(RaceTile tile : pathTiles) {
            cheat(tile, movements);
        }

    }

    public long solve(int savedPicoseconds) {
        return numberOfCheatsPerSavedPs.entrySet().stream()
                .filter(entry -> entry.getKey() >= savedPicoseconds)
                .mapToLong(Map.Entry::getValue)
                .sum();
    }

    private Set<Vector2> movements(int cheatTime) {

        // When moving with a cheat time of n, there are a wide range of different movements.
        // So for cheat time = 2, there are 12 possible movements
        // For cheat time = 3 there are 24 possible movements
        // For cheat time = 20, there are 840 possible movements
        
        Set<Vector2> movements = new HashSet<>();

        for(int xTime = 0; xTime <= cheatTime; xTime++) {
            int remainingTime = cheatTime-xTime;
            for(int yTime = 0; yTime <= remainingTime; yTime++) {
                movements.add(new Vector2(xTime, yTime));
                movements.add(new Vector2(-xTime, yTime));
                movements.add(new Vector2(xTime, -yTime));
                movements.add(new Vector2(-xTime, -yTime));
            }
        }

        movements.remove(new Vector2(0,0));

        return movements;
    }


    private void cheat(RaceTile tileA, Set<Vector2> movements) {

        long distanceA = tileA.getDistance();
        for(Vector2 movement : movements) {
            Vector2 position = Vector2.transform(tileA.getPosition(), movement);
            if(isInbounds(position.getY(), position.getX())) {
                RaceTile tileB = map[position.getY()][position.getX()];
                if(tileB.isEmpty()) {
                    long distanceAB = Vector2.manhattanDistance(tileA.getPosition(),tileB.getPosition());
                    long distanceBE = tileB.getDistanceToEnd();
                    long totalDistance = distanceA + distanceAB + distanceBE;
                    long savedTime = end.getDistance() - totalDistance;
                    if(savedTime > 0) {
                        long savedTimeCount = numberOfCheatsPerSavedPs.getOrDefault(savedTime, 0L);
                        numberOfCheatsPerSavedPs.put(savedTime, savedTimeCount + 1);
                    }

                }
            }
        }

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

}
