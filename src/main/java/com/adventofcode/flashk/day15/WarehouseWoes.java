package com.adventofcode.flashk.day15;

import com.adventofcode.flashk.common.Vector2;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WarehouseWoes {

    private static final char EMPTY = '.';
    private static final char WALL = '#';
    private static final char BOX = 'O';
    private static final char UP = '^';
    private static final char DOWN = 'v';
    private static final char LEFT = '<';
    private static final char RIGHT = '>';


    private Map<Character,Vector2> directions = new HashMap<>();

    private int rows = 0;
    private int cols = 0;
    private char[][] map;
    private final char[] movements;
    private Vector2 robotPos;

    public WarehouseWoes(List<String> inputs) {

        boolean isMap = true;
        int row = 0;

        // Map
        Iterator<String> inputIterator = inputs.iterator();

        while(inputIterator.hasNext() && isMap) {
            String input = inputIterator.next();
            if(input.equals(StringUtils.EMPTY)) {
                isMap = false;
            } else {
                addRow(input, row);
                row++;
            }
            inputIterator.remove();
        }

        // movements

        movements = String.join(StringUtils.EMPTY, inputs).toCharArray();

        // Robot directions
        directions.put(UP, Vector2.down());
        directions.put(DOWN, Vector2.up());
        directions.put(LEFT, Vector2.left());
        directions.put(RIGHT, Vector2.right());
    }

    private void addRow(String input, int row) {

        if(map == null) {
            rows = input.length();
            cols = rows;
            map = new char[rows][];
        }

        map[row] = input.toCharArray();

        int robotCol = input.indexOf("@");
        if(robotCol != -1) {
            robotPos = new Vector2(robotCol, row);
        }

    }

    public long solveA() {

        int movementIndex = 0;
        //Array2DUtil.paint(map);
        while(movementIndex < movements.length) {

            Vector2 direction = directions.get(movements[movementIndex]);
            // while si el robot pudiese repetir el mismo movimiento hasta encontrar un bache
            // if si solo realiza un movimiento una vez
            if(move(direction, robotPos.getY(), robotPos.getX())){
                map[robotPos.getY()][robotPos.getX()] = EMPTY;
                robotPos.transform(direction);
                //Array2DUtil.paint(map);
            }

            movementIndex++;
        }

        return sumGPS();
    }

    private long sumGPS() {
        long result = 0;
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++) {
                   if(map[row][col] == BOX) {
                       result += (100L*row)+col;
                   }
            }
        }
        return result;
    }


    private boolean move(Vector2 direction, int row, int col) {

        if(map[row][col] == EMPTY) {
            return true;
        } else if(map[row][col] == WALL) {
            return false;
        }

        int nextRow = direction.getY()+row;
        int nextCol = direction.getX()+col;

        boolean canMove = move(direction, nextRow, nextCol);

        if(canMove) {
            map[nextRow][nextCol] = map[row][col];
        }

        return canMove;
    }
}
