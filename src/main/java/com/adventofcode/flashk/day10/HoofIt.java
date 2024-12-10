package com.adventofcode.flashk.day10;

import com.adventofcode.flashk.common.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HoofIt {

    private Tile[][] map;
    private int rows;
    private int cols;

    private List<Tile> initialTiles = new ArrayList<>();
    private Set<Tile> reachedTiles = new HashSet<>();

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

    public long solveA() {

        long result = 0;
        for(Tile initialTile : initialTiles) {
            result += findPath(initialTile, -1);
            reachedTiles.clear();
        }
        return result;
    }

    public long solveB() {

        long result = 0;
        for(Tile initialTile : initialTiles) {
            result += findPathB(initialTile, -1);
            //reachedTiles.clear();
        }
        return result;
    }

    private long findPath(Tile tile, int previousHeight) {

        if(tile.isVisited()) {
            return 0;
        }

        if(tile.getHeight()-previousHeight != 1) {
            return 0;
        }

        if(tile.getHeight() == 9 && !reachedTiles.contains(tile)) {
            reachedTiles.add(tile);
            return 1;
        }

        long result = 0;

        // revisar condiciones de revisitado
        tile.setVisited(true);

        Set<Tile> adjacentTiles = getAdjacentTiles(tile);

        for(Tile adjacentTile : adjacentTiles) {
            result += findPath(adjacentTile, tile.getHeight());
        }

        tile.setVisited(false);

        return result;

    }

    private long findPathB(Tile tile, int previousHeight) {

        if(tile.isVisited()) {
            return 0;
        }

        if(tile.getHeight()-previousHeight != 1) {
            return 0;
        }

        if(tile.getHeight() == 9) {
            return 1;
        }

        long result = 0;

        // revisar condiciones de revisitado
        tile.setVisited(true);

        Set<Tile> adjacentTiles = getAdjacentTiles(tile);

        for(Tile adjacentTile : adjacentTiles) {
            result += findPathB(adjacentTile, tile.getHeight());
        }

        tile.setVisited(false);

        return result;

    }

    private Set<Tile> getAdjacentTiles(Tile tile) {
        Set<Tile> adjacentTiles = new HashSet<>();

        Vector2 initialPosition = tile.getPosition();

        Vector2 nextTile = Vector2.transform(initialPosition, Vector2.left());
        if(!isOutOfBounds(nextTile)) {
            adjacentTiles.add(map[nextTile.getY()][nextTile.getX()]);
        }

        nextTile = Vector2.transform(initialPosition, Vector2.right());
        if(!isOutOfBounds(nextTile)) {
            adjacentTiles.add(map[nextTile.getY()][nextTile.getX()]);
        }

        nextTile = Vector2.transform(initialPosition, Vector2.up());
        if(!isOutOfBounds(nextTile)) {
            adjacentTiles.add(map[nextTile.getY()][nextTile.getX()]);
        }

        nextTile = Vector2.transform(initialPosition, Vector2.down());
        if(!isOutOfBounds(nextTile)) {
            adjacentTiles.add(map[nextTile.getY()][nextTile.getX()]);
        }

        return adjacentTiles;
    }

    private boolean isOutOfBounds(Vector2 pos) {
        return (pos.getY() < 0 || pos.getY() >= rows || pos.getX() < 0 || pos.getX() >= cols);
    }


}
