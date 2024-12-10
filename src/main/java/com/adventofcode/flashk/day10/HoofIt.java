package com.adventofcode.flashk.day10;

import com.adventofcode.flashk.common.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HoofIt {

    private final Tile[][] map;
    private final int rows;
    private final int cols;

    private final List<Tile> initialTiles = new ArrayList<>();
    private final Set<Tile> reachedTiles = new HashSet<>();


    public HoofIt(int[][] inputs) {
        this.rows = inputs.length;
        this.cols = inputs[0].length;
        this.map = new Tile[rows][cols];

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                Tile tile =  new Tile(new Vector2(col, row), inputs[row][col]);
                this.map[row][col] = tile;
                if(inputs[row][col] == 0) {
                    initialTiles.add(tile);
                }
            }
        }
    }

    public long solve(boolean rating) {

        long result = 0;
        for(Tile initialTile : initialTiles) {
            result += findPath(initialTile, -1, rating);
            reachedTiles.clear();
        }
        return result;
    }


    private long findPath(Tile tile, int previousHeight, boolean rating) {

        if(tile.isVisited()) {
            return 0;
        }

        if(tile.getHeight()-previousHeight != 1) {
            return 0;
        }

        if((tile.getHeight() == 9) && !rating && !reachedTiles.contains(tile)){
            reachedTiles.add(tile);
            return 1;
        } else if((tile.getHeight() == 9) && rating) {
            return 1;
        }

        long result = 0;

        tile.setVisited(true);

        Set<Tile> adjacentTiles = getAdjacentTiles(tile);

        for(Tile adjacentTile : adjacentTiles) {
            result += findPath(adjacentTile, tile.getHeight(), rating);
        }

        tile.setVisited(false);

        return result;

    }

    private Set<Tile> getAdjacentTiles(Tile tile) {
        Set<Tile> adjacentTiles = new HashSet<>();

        Vector2 initialPos = tile.getPosition();

        Vector2 nextPos = Vector2.transform(initialPos, Vector2.left());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(map[nextPos.getY()][nextPos.getX()]);
        }

        nextPos = Vector2.transform(initialPos, Vector2.right());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(map[nextPos.getY()][nextPos.getX()]);
        }

        nextPos = Vector2.transform(initialPos, Vector2.up());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(map[nextPos.getY()][nextPos.getX()]);
        }

        nextPos = Vector2.transform(initialPos, Vector2.down());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(map[nextPos.getY()][nextPos.getX()]);
        }

        return adjacentTiles;
    }

    private boolean isInbounds(Vector2 pos) {
        return (pos.getY() >= 0 && pos.getY() < rows && pos.getX() >= 0 && pos.getX() < cols);
    }


}
