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

    // TODO add also start direction
    private long dijkstra(Tile start, Vector2 direction) {

        reset();

        // Initialize start tile
        start.setScore(0);
        start.setDirection(direction);
        //if(start.getDirection() == null) {
        //    start.setDirection(Vector2.right());
        //}

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

    public long solveB(){


        // Idea:

        // 1. Primero aplicamos un dijkstra normal para encontrar el camino más corto.
        // 2, Metemos en una lista los tiles del camino más corto.

        // Ahora recorremos cada tile del camino más corto
        // 1. Calculamos los adyacentes de un tile.
        // 2. Si la distancia del tile adyacente al inicio + la distancia del tile al final es igual que la distancia
        // del camino más corto, entonces estamos ante un camino alternativo.

        long score = dijkstra(startTile, Vector2.right());
        paint();
        // Parte 1, encontrar las intersecciones que hay en el camino más corto

        Set<Tile> intersections = findPathIntersections();
        //Set<Tile> intersections = findPathSections();

        for(Tile intersection : intersections) {

            // calcular dijkstra para adyacentes
            Set<Tile> adjacents = getAdjacentsIncludingVisited(intersection);
            // Add also direction

            for(Tile adjacent : adjacents) {
                //if(adjacent.isPath()) {
                //    continue;
                //}
                //Vector2 direction = Vector2.substract(intersection.getPosition(), adjacent.getPosition());
                Vector2 direction = Vector2.substract(adjacent.getPosition(),intersection.getPosition());
                dijkstra(adjacent, direction);
                long adjacentScore = endTile.getScore() + startTile.getScore();
                if(adjacentScore == score) {
                    fillPath(adjacent, endTile);
                    fillPath(adjacent, startTile);
                    paint();
                    System.out.println();
                }
            }
        }
        // Pasada 2, buscamos caminos alternativos en cada una de las intersecciones.

        startTile.setPath(true);
        endTile.setPath(true);

        paint();
        return countPathTiles();
    }

    private void reverseDijkstra() {

        // Initialize start tile
        endTile.setScoreToEnd(0);

        //end.setDirection(direction);

        //if(start.getDirection() == null) {
        //    start.setDirection(Vector2.right());
        //}

        // Execute reverse dijkstra

        // This implementation must be executed after dijkstra.

        // Initialization:
        // - The starting tile must be the end tile.
        // - Sets the score to the end to 0.
        // - Add the ending tile to the queue.

        // While the queue is not empty:
        // - Take a tile from the queue
        // - Check it as visited reversed.
        // - Obtain adjacents.

        // Now for the adjacents:
        // - Calculate the adjacent direction

        Deque<Tile> tiles = new ArrayDeque<>();
        tiles.add(endTile);

        while(!tiles.isEmpty()) {

            Tile tile = tiles.poll();
            tile.setVisitedReverse(true);
            tile.setPath(true);

            // Calcular adyacentes.
            Set<Tile> adjacentTiles = getAdjacentsReverse(tile);
            for(Tile adjacentTile : adjacentTiles) {

                // TODO Old code - compare the position to the tile to obtain a direction and then compare directions

                //long scoreAdjacentToTile = newDirection.equals(tile.getDirection()) ? 1 : 1001;
                // TODO or maybe compare directions directly.

                // Tiles should be added to the queue only if they can meet the following formula:
                // d(S,E) = d(S,A) + d(A,B) + d(B,E)

                // Where:
                // S: Starting tile
                // E: Ending tile
                // A: Adjacent tile
                // B: Tile

                // Known parameters:
                // d(S,E): endTile.getScore()
                // d(S,A): adjacentTile.getScore()
                // d(A,B): 1 if A and B directions are the same, 1001 if A and B directions is different.
                // d(B,E): tile.getScoreToEnd()

                // d(S,A)
                long scoreAdjacentTile = adjacentTile.getScore();

                // d(A,B)
                Vector2 newDirection = Vector2.substract(tile.getPosition(), adjacentTile.getPosition());
                long scoreAdjacentToTile = 1;
                if(tile != endTile && !newDirection.equals(tile.getDirectionReverse())) {
                    scoreAdjacentToTile += 1000;
                } /*else if(tile != endTile && !newDirection.equals(adjacentTile.getDirection())) {
                    scoreAdjacentToTile += 1000;
                }*/
                if(adjacentTile.getDirection().equals(newDirection) && !adjacentTile.getParent().getDirection().equals(newDirection)) {
                    scoreAdjacentToTile += 1000;
                }


                // Comparar dirección de adjacentTile con Tile.

                // d(B,E)
                long scoreTileToEnd = tile.getScoreToEnd();

                //long scoreAdjacentToTile =  tile.getScore() - adjacentTile.getScore();

                // TODO Revisar testSolvePart2Sample2
                // El nodo de arriba a la izquierda, con un score de 11048 no debería ser capaz de llegar a end
                // porque lo máximo que podría sumar es un punto, y no 1001.
                // Por lo tanto, esta lógica de 1,1001,-999 no es del todo correcta.

                //if(scoreAdjacentToTile == 1 || scoreAdjacentToTile == 1001 || scoreAdjacentToTile == -999){
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
        paint();

    }

    private long countPathTiles() {
        long count = 0;
        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (map[row][col].isPath()) {
                    count++;
                }
            }
        }
        return count;
    }

    private Set<Tile> findPathIntersections() {
        Set<Tile> intersections = new HashSet<>();

        Tile end = map[endPos.getY()][endPos.getX()];
        Tile start = map[startPos.getY()][startPos.getX()];

        Tile current = end;
        do {
            current.setPath(true);
            if(getAdjacentsIncludingVisited(current).size() > 2) {
                intersections.add(current);
            }
            current = current.getParent();
        } while(current != start);

        start.setPath(true);

        return intersections;
    }


    private void reset() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                map[row][col].setVisited(false);
                map[row][col].setScore(Long.MAX_VALUE);
                map[row][col].setParent(null);
                //map[row][col].setPath(false);
            }
        }
    }

    private void fillPath(Tile start, Tile end) {
        
        Tile current = end;
        while(current != start) {
            current = current.getParent();
            current.setPath(true);
        }

        start.setPath(true);
        end.setPath(true); // just in case

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

    private Set<Tile> getAdjacentsIncludingVisited(Tile parent) {
        Set<Tile> adjacents = new HashSet<>();
        for(Vector2 dir : directions) {
            Vector2 newPos = Vector2.transform(parent.getPosition(), dir);
            Tile newTile = map[newPos.getY()][newPos.getX()];
            if(!newTile.isWall()) {
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
