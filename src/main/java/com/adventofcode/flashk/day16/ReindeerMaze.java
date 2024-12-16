package com.adventofcode.flashk.day16;

import com.adventofcode.flashk.common.Vector2;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class ReindeerMaze {

    private static final char WALL = '#';

    private Tile[][] map;
    private int rows;
    private int cols;
    private Vector2 startPos;
    private Vector2 endPos;
    private Tile startTile;

    private Set<Vector2> directions = Set.of(Vector2.right(), Vector2.left(), Vector2.up(), Vector2.down());

    public ReindeerMaze(char[][] inputs) {
        rows = inputs.length;
        cols = inputs[0].length;
        map = new Tile[rows][cols];

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                map[row][col] = new Tile(new Vector2(col, row), inputs[row][col]);
                if(map[row][col].isStart()) {
                    startPos = map[row][col].getPosition();
                    startTile = map[row][col];
                } else if(map[row][col].isEnd()) {
                    endPos = map[row][col].getPosition();
                }
            }
        }

    }

    // Rehacer con cola de prioridad

    public long solveA2() {
        PriorityQueue<Tile> tiles = new PriorityQueue<>();


        // inicio
        startTile.setScore(0);
        tiles.add(startTile);
        while(!tiles.isEmpty()) {
            Tile nextTile = tiles.poll();
            nextTile.setVisited(true);

            // Calcular adyacentes.
            Set<Tile> adjacentTiles = getAdjacents(nextTile);
        }
    }

    private Set<Tile> getAdjacents(Tile nextTile) {

    }

    public long solveA() {
        long result = findPath(new Vector2(startPos), Vector2.right(), 0, Long.MAX_VALUE);

        //paint();
        return result;
    }

    // Casos base
    // - Llegar a E
    // - Llegar a un wall

    private long findPath(Vector2 position, Vector2 direction, long score, long bestScore) {
        if(map[position.getY()][position.getX()].isWall()) {
            return Long.MAX_VALUE;
        } else if(map[position.getY()][position.getX()].isVisited()) {
            return Long.MAX_VALUE; // o puede que score, pero no queremos bucles infinitos
        } else if(map[position.getY()][position.getX()].isEnd()) {
            /*if(score < bestScore) {
                System.out.println("Result: "+score);
                paint();
            }*/
            return Math.min(score, bestScore);
        }

        // Calcular adyacentes
        // - 4 posibles movimientos horizontales
        // - 2 posibles giros: izquierda, derecha, no tiene sentido girar 180º
        map[position.getY()][position.getX()].setVisited(true);

        long newBestScore = bestScore;
        for(Vector2 newDir :directions) {

            long additionalScore = direction.equals(newDir) ? 1 : 1001;
            Vector2 newPos = Vector2.transform(position, newDir);


            if(!map[newPos.getY()][newPos.getX()].isVisited()) {
                long newScore = findPath(newPos, newDir, score + additionalScore, bestScore);
                newBestScore = Math.min(newScore, newBestScore);
            }
        }

        map[position.getY()][position.getX()].setVisited(false);

        return newBestScore;

    }


    private void paint(){
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(map[row][col].isVisited()) {
                    System.out.print('O');
                } else {
                    System.out.print(map[row][col].getValue());
                }
            }
            System.out.println();
        }
    }
}
