package com.adventofcode.flashk.day16;

import com.adventofcode.flashk.common.Vector2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class ReindeerMaze {
    private Tile[][] map;
    private int rows;
    private int cols;
    private Vector2 startPos;
    private Vector2 endPos;
    private Tile startTile;
    private Tile endTile;

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
                    endTile = map[row][col];
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
        return dijkstra(startTile, Vector2.right());
    }

    private long dijkstra(Tile start, Vector2 direction) {

        reset();

        // Initialize start tile
        start.setScore(0);
        start.setDirection(direction);

        // Execute dijkstra
        PriorityQueue<Tile> tiles = new PriorityQueue<>();
        tiles.add(start);

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
                    adjacentTile.setPartialScore(scoreIncrementer);
                    adjacentTile.setParent(tile);
                    adjacentTile.setDirection(newDirection);
                    tiles.add(adjacentTile);
                }

            }
        }
        //paint();
        return map[endPos.getY()][endPos.getX()].getScore();
    }

    /*
    Subproblem for part 2

    Looking at the first example there are certain intersections that allow alternative shortest paths:
    #.#.###
    #..OOOO
    ###O#O#
    #OOO#O.
    #O#O#O#
    #OOOOO#
    #O###.#
    #O..#..
    #######

    Filling with walls to analyze the smaller problem:

    ########
    #.....S#
    ###.#.##
    #...#..#
    #.#.#.##
    #.....##
    #.###.##
    #E..#..#
    ########

    Path 1:       Path 2:	    Path 3:
    ########      ########      ########
    #....11#      #..2222#      #..3333#
    ###.#1##      ###2#.##      ###3#.##
    #...#1.#      #..2#..#      #333#..#
    #.#.#1##      #.#2#.##      #3#.#.##
    #11111##      #222..##      #3....##
    #1###.##      #2###.##      #3###.##
    #1..#..#      #2..#..#      #3..#..#
    ########      ########      ########

    - 3 turns     - 3 turns     - 3 turns
    - 11 steps    - 11 steps    - 11 steps

     */

    public long solveB2() {
        long result = 0;
        dijkstra(startTile, Vector2.right());
        reverseDijkstra();
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(map[row][col].isPath()) {
                    result++;
                }
            }
        }

        return result;
    }

    /// Executes a reverse dijkstra that starts at the end node and attempts to reach the start node.
    ///
    /// This method must be executed after [dijkstra(Tile start, Vector2 direction)]()
    private void reverseDijkstra() {

        // Initialize start tile
        endTile.setScoreToEnd(0);

        Deque<Tile> tiles = new ArrayDeque<>();
        tiles.add(endTile);

        while(!tiles.isEmpty()) {

            Tile tile = tiles.poll();
            tile.setVisitedReverse(true);
            tile.setPath(true);

            // Calcular adyacentes.
            Set<Tile> adjacentTiles = getAdjacentsReverse(tile);
            for(Tile adjacentTile : adjacentTiles) {

                // Tiles should be added to the queue only if they can meet the following formula:
                // d(S,E) == estimated score
                // estimated score = d(S,A,E) = d(S,A) + d(A,B) + d(B,E)

                // Where:
                // S: Starting tile
                // E: Ending tile
                // A: Adjacent tile
                // B: Tile
                // Where nodes have this order: S -> A -> B -> E

                // Known parameters:
                // d(S,E): endTile.getScore()
                // d(S,A): adjacentTile.getScore()
                // d(A,B): 1 if A and B directions are the same, 1001 if A and B directions is different.
                // d(B,E): tile.getScoreToEnd()
                //
                // Also adjacent tile score must be modified by 1000 when taking corners.

                Vector2 newDirection = Vector2.substract(adjacentTile.getPosition(), tile.getPosition());

                // d(S,A)
                long scoreAdjacentTile = calculateAdjacentTileScore(adjacentTile, newDirection);

                // d(A,B)
                long scoreAdjacentToTile = calculateAdjacentTileToTileScore(adjacentTile, tile, newDirection);

                // d(B,E)
                long scoreTileToEnd = tile.getScoreToEnd();

                // estimated score = d(S,A) + d(A,B) + d(B,E)
                long estimatedScore = scoreAdjacentTile + scoreAdjacentToTile + scoreTileToEnd;
                if(estimatedScore == endTile.getScore()) {
                    // Adjacent tile has a path that is the same as the best score

                    // d(A,E) = d(A,B) + d(B,E)
                    adjacentTile.setScoreToEnd(scoreAdjacentToTile+scoreTileToEnd);
                    adjacentTile.setDirectionReverse(newDirection);

                    tiles.add(adjacentTile);

                }

            }
        }
        //paint();

    }

    private long calculateAdjacentTileScore(Tile adjacentTile, Vector2 newDirection) {

        long scoreAdjacentTile = adjacentTile.getScore();

        // If there is a corner, adjacent tile must modify its score
        Vector2 reversedNewDirection = Vector2.multiply(newDirection,-1);
        if(!reversedNewDirection.equals(adjacentTile.getDirection())) {
            scoreAdjacentTile += 1000;
        }

        return scoreAdjacentTile;
    }

    private long calculateAdjacentTileToTileScore(Tile adjacentTile, Tile tile, Vector2 newDirection) {
        long scoreAdjacentToTile;
        if(tile == endTile) {
            scoreAdjacentToTile = 1;
        } else {
            scoreAdjacentToTile = newDirection.equals(tile.getDirectionReverse()) ? 1 : 1001;
        }

        return scoreAdjacentToTile;
    }

    private void reset() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                map[row][col].setVisited(false);
                map[row][col].setScore(Long.MAX_VALUE);
                map[row][col].setParent(null);
            }
        }
    }

    private Set<Tile> getAdjacents(Tile parent) {
        Set<Tile> adjacents = new HashSet<>();
        for(Vector2 dir : directions) {
            Vector2 newPos = Vector2.transform(parent.getPosition(), dir);
            Tile newTile = map[newPos.getY()][newPos.getX()];
            if(!newTile.isWall() && !newTile.isVisited()) {
                adjacents.add(newTile);
            }
        }
        return adjacents;
    }

    private Set<Tile> getAdjacentsReverse(Tile parent) {
        Set<Tile> adjacents = new HashSet<>();
        for(Vector2 dir : directions) {
            Vector2 newPos = Vector2.transform(parent.getPosition(), dir);
            Tile newTile = map[newPos.getY()][newPos.getX()];
            if(!newTile.isWall() && !newTile.isVisitedReverse()) {
                adjacents.add(newTile);
            }
        }
        return adjacents;

    }

    private void paint(){
        //fillPath();
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
