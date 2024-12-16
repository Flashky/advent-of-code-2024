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

    /*
       DIJKSTRA (Grafo G, nodo_fuente s)
       para u ∈ V[G] hacer
           distancia[u] = INFINITO
           padre[u] = NULL
           visto[u] = false
       distancia[s] = 0
       adicionar (cola, (s, distancia[s]))
       mientras que cola no es vacía hacer
           u = extraer_mínimo(cola)
           visto[u] = true
           para todos v ∈ adyacencia[u] hacer
               si ¬ visto[v]
                   si distancia[v] > distancia[u] + peso (u, v) hacer
                       distancia[v] = distancia[u] + peso (u, v)
                       padre[v] = u
                       adicionar(cola,(v, distancia[v]))
     */

    public long solveA2() {

        // Initialize start tile
        startTile.setScore(0);
        startTile.setDirection(Vector2.right());

        // Execute dijkstra
        PriorityQueue<Tile> tiles = new PriorityQueue<>();
        tiles.add(startTile);

        while(!tiles.isEmpty()) {
            Tile tile = tiles.poll();
            tile.setVisited(true);

            // Calcular adyacentes.
            Set<Tile> adjacentTiles = getAdjacents(tile);
            for(Tile adjacentTile : adjacentTiles) {

                Vector2 newDirection = Vector2.substract(adjacentTile.getPosition(), tile.getPosition());
                long scoreIncrementer = newDirection.equals(tile.getDirection()) ? 1 : 1001;
                if(adjacentTile.getScore() > tile.getScore() + scoreIncrementer) {
                    adjacentTile.setScore(tile.getScore() + scoreIncrementer);
                    adjacentTile.setParent(tile);
                    adjacentTile.setDirection(newDirection);
                    tiles.add(adjacentTile);
                }

            }
        }
        //paint();
        return map[endPos.getY()][endPos.getX()].getScore();
    }

    private void fillPath() {
        Tile end = map[endPos.getY()][endPos.getX()];
        Tile start = map[startPos.getY()][startPos.getX()];
        Tile current = end;
        while(current != start) {
            current = current.getParent();
            current.setPath(true);
        }
    }
    private Set<Tile> getAdjacents(Tile parent) {
        Set<Tile> adjacents = new HashSet<>();
        for(Vector2 dir : directions) {
            Vector2 newPos = Vector2.transform(parent.getPosition(), dir);
            Tile newTile = map[newPos.getY()][newPos.getX()];
            if(!newTile.isWall() & !newTile.isVisited()) {
                adjacents.add(newTile);
            }
        }
        return adjacents;

    }

    private void paint(){
        fillPath();
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(map[row][col].isPath()) {
                    System.out.print('O');
                } else {
                    System.out.print(map[row][col].getValue());
                }
            }
            System.out.println();
        }
    }


}
