package com.adventofcode.flashk.day16;

import com.adventofcode.flashk.common.Vector2;
import org.jetbrains.annotations.NotNull;

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
        return dijkstra(startTile);
    }

    private long dijkstra(Tile start) {

        reset();

        // Initialize start tile
        start.setScore(0);
        start.setDirection(Vector2.right());

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
    public long solveB(){
        // Idea:

        // 1. Primero aplicamos un dijkstra normal para encontrar el camino más corto.
        // 2, Metemos en una lista los tiles del camino más corto.

        // Ahora recorremos cada tile del camino más corto
        // 1. Calculamos los adyacentes de un tile.
        // 2. Si la distancia del tile adyacente al inicio + la distancia del tile al final es igual que la distancia
        // del camino más corto, entonces estamos ante un camino alternativo.

        long score = dijkstra(startTile);

        // Parte 1, encontrar las intersecciones que hay en el camino más corto

        Set<Tile> intersections = findPathIntersections();

        for(Tile intersection : intersections) {
            //intersection.setPath(true);
            // calcular dijkstra para adyacentes que no formen parte de path
            Set<Tile> adjacents = getAdjacentsIncludingVisited(intersection);
            for(Tile adjacent : adjacents) {
                if(adjacent.isPath()) {
                    continue;
                }
                dijkstra(adjacent);
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

        //paint();
        return countPathTiles();
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

    private void fillPath() {
        Tile end = map[endPos.getY()][endPos.getX()];
        Tile start = map[startPos.getY()][startPos.getX()];
        Tile current = end;
        while(current != start) {
            current = current.getParent();
            current.setPath(true);
        }
    }

    private void fillPath(Tile start, Tile end) {
        //Tile start = map[startPos.getY()][startPos.getX()];
        //Tile end = map[endPos.getY()][endPos.getX()];

        Tile current = end;
        while(current != start) {
            current = current.getParent();
            current.setPath(true);
        }

        end.setPath(true);
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
