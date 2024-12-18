package com.adventofcode.flashk.day18;

import com.adventofcode.flashk.common.Vector2;
import com.adventofcode.flashk.day16.Tile;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class RamRun {

    private int rows;
    private int cols;
    private ByteCell[][] map;
    private List<Vector2> corruptedCells;
    private Set<Vector2> directions = Set.of(Vector2.right(), Vector2.left(), Vector2.up(), Vector2.down());

    public RamRun(List<String> inputs, int size) {
        // Si size es 6, entonces hay números que van del 0..6 -> size = 7
        // Adicionalmente, podemos rellenar los bordes del mapa de muros para hacer más fácil dijkstra, pero esto
        // desplazaría las posiciones de todos los objetos, así que, ¿vale la pena?
        rows = size+1;
        cols = size+1;
        map = new ByteCell[rows][cols];

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                map[row][col] = new ByteCell(new Vector2(col, row));
            }
        }

        // Now read corrupted items.
        corruptedCells = inputs.stream().map(Vector2::new).toList();

    }

    //    **DIJKSTRA** (Grafo _G_, nodo_fuente _s_)
    //       **para** _u_ ∈ _V[G]_ **hacer**
    //           distancia[_u_] = INFINITO
    //           padre[_u_] = NULL
    //           visto[_u_] = **false**
    //       distancia[_s_] = 0
    //       adicionar (cola, (s, distancia[_s_]))
    //       **mientras que** cola no es vacía **hacer**
    //           _u_ = extraer_mínimo(cola)
    //           visto[_u_] = **true**
    //           **para** todos _v_ ∈ adyacencia[_u_] **hacer**
    //               **si** ¬ visto[_v_]
    //                   **si** distancia[_v_] > distancia[_u_] + peso (_u_, _v_) **hacer**
    //                       distancia[_v_] = distancia[_u_] + peso (_u_, _v_)
    //                       padre[_v_] = _u_
    //                       adicionar(cola,(_v_, distancia[_v_]))

    public long solveA(int numberOfBytes) {

        corrupt(numberOfBytes);
        paint();
        dijkstra(map[0][0]);
        return map[rows-1][cols-1].getDistance();

    }


    public String solveB() {

        Iterator<Vector2> corruptionIterator = corruptedCells.iterator();

        Vector2 lastCorruptedCell;
        do {
            reset();
            lastCorruptedCell = corruptionIterator.next();
            map[lastCorruptedCell.getY()][lastCorruptedCell.getX()].corrupt();
            //paint();
            dijkstra(map[0][0]);
        } while(map[rows-1][cols-1].isReachable());

        return lastCorruptedCell.getX()+","+lastCorruptedCell.getY();

    }

    private void corrupt(int numberOfBytes){
        corruptedCells.stream().limit(numberOfBytes).forEach(c -> map[c.getY()][c.getX()].corrupt());
    }

    private void dijkstra(ByteCell start) {

        start.setDistance(0);

        PriorityQueue<ByteCell> queue = new PriorityQueue<>();
        queue.add(start);

        while(!queue.isEmpty()) {
            ByteCell parent = queue.poll();
            parent.setVisited(true);
            Set<ByteCell> adjacents = getAdjacents(parent);

            for(ByteCell adjacent : adjacents) {
                if(adjacent.getDistance() > parent.getDistance()+1) {
                    adjacent.setDistance(parent.getDistance()+1);
                    adjacent.setParent(parent);
                    queue.add(adjacent);
                }
            }
        }
    }

    private Set<ByteCell> getAdjacents(ByteCell cell) {

        Set<ByteCell> adjacents = new HashSet<>();
        for(Vector2 dir : directions) {
            Vector2 newPos = Vector2.transform(cell.getPosition(), dir);

            if(isInbounds(newPos.getY(), newPos.getX())) {
                ByteCell newCell = map[newPos.getY()][newPos.getX()];

                if (!newCell.isCorrupted() && !newCell.isVisited()) {
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
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++) {
                map[row][col].reset();
            }
        }
    }

    private void paint() {
        System.out.println();
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                map[row][col].paint();
            }
            System.out.println();
        }
    }
}
