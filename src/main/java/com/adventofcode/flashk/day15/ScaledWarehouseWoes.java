package com.adventofcode.flashk.day15;

import com.adventofcode.flashk.common.Array2DUtil;
import com.adventofcode.flashk.common.Vector2;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScaledWarehouseWoes {

    private static final char ROBOT = '@';
    private static final char EMPTY = '.';
    private static final char WALL = '#';
    private static final char BOX = 'O';
    private static final char BOX_START = '[';
    private static final char BOX_END = ']';

    private static final char UP = '^';
    private static final char DOWN = 'v';
    private static final char LEFT = '<';
    private static final char RIGHT = '>';


    private Map<Character, Vector2> directions = new HashMap<>();

    private int rows = 0;
    private int cols = 0;
    private char[][] map;
    private final char[] movements;
    private Vector2 robotPos;

    public ScaledWarehouseWoes(List<String> inputs) {

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
            cols = rows * 2;
            map = new char[rows][cols];
        }

        char[] inputRow = input.toCharArray();
        int col = 0;
        for(char tile : inputRow) {
            switch (tile) {
                case ROBOT -> {
                    map[row][col++] = ROBOT;
                    map[row][col++] = EMPTY;
                }
                case BOX -> {
                    map[row][col++] = BOX_START;
                    map[row][col++] = BOX_END;
                }
                default -> {
                    map[row][col++] = tile;
                    map[row][col++] = tile;
                }
            }

        }

        String scaledInput = new String(map[row]);
        int robotCol = scaledInput.indexOf(ROBOT);
        if(robotCol != -1) {
            robotPos = new Vector2(robotCol, row);
        }

    }

    public long solveB() {
        int movementIndex = 0;
        while(movementIndex < movements.length) {
            move(movements[movementIndex]);
            movementIndex++;
        }

        return sumGPS();
    }

    private void move(char movement) {
        Vector2 direction = directions.get(movement);
        boolean canMove;
        if(movement == LEFT || movement == RIGHT) {
            canMove = moveH(direction, robotPos.getY(), robotPos.getX());
        } else {
            canMove = moveV(direction, robotPos.getY(), robotPos.getX());
            if(canMove) {
                commitMoveV(direction, robotPos.getY(), robotPos.getX());
            }
        }

        // Update old robot position and move it

        if(canMove) {
            map[robotPos.getY()][robotPos.getX()] = EMPTY;
            robotPos.transform(direction);
        }

    }

    private boolean moveH(Vector2 direction, int row, int col) {

        if(map[row][col] == EMPTY) {
            return true;
        } else if(map[row][col] == WALL) {
            return false;
        }

        int nextRow = direction.getY()+row;
        int nextCol = direction.getX()+col;

        boolean canMove = moveH(direction, nextRow, nextCol);

        if(canMove) {
            map[nextRow][nextCol] = map[row][col];
        }

        return canMove;
    }

    private boolean moveV(Vector2 direction, int row, int col) {
        if(map[row][col] == EMPTY) {
            return true;
        } else if(map[row][col] == WALL) {
            return false;
        }

        int nextRow = direction.getY()+row;

        boolean canMove;
        if(map[row][col] == BOX_START) {
            canMove = moveV(direction, nextRow, col) && moveV(direction, nextRow,col+1);
        } else if(map[row][col] == BOX_END){
            canMove = moveV(direction, nextRow, col) && moveV(direction, nextRow, col-1);
        } else {
            // ROBOT
            canMove = moveV(direction, nextRow, col);
        }

        return canMove;

    }

    private void commitMoveV(Vector2 direction, int row, int col) {
        // TODO Puede que esto no haga falta
        if(map[row][col] == EMPTY) {
            return;
        } else if(map[row][col] == WALL) {
            return;
        }

        int nextRow = direction.getY()+row;

        //boolean canMove;
        if(map[row][col] == BOX_START) {
            commitMoveV(direction, nextRow, col);
            commitMoveV(direction, nextRow,col+1);

            map[nextRow][col] = map[row][col];
            map[nextRow][col+1] = map[row][col+1];
            map[row][col] = EMPTY;
            map[row][col+1] = EMPTY;
        } else if(map[row][col] == BOX_END){
            commitMoveV(direction, nextRow, col);
            commitMoveV(direction, nextRow, col-1);
            map[nextRow][col] = map[row][col];
            map[nextRow][col-1] = map[row][col-1];
            map[row][col] = EMPTY;
            map[row][col-1] = EMPTY;

        } else {
            // ROBOT
            commitMoveV(direction, nextRow, col);
            map[nextRow][col] = map[row][col];
            map[row][col] = EMPTY;
        }

    }

    private long sumGPS() {
        long result = 0;

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++) {
                if(map[row][col] == BOX_START) {
                    result += (100L*row)+col;
                }
            }
        }
        return result;
    }
}
