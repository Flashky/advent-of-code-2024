package com.adventofcode.flashk.day06;

import com.adventofcode.flashk.common.Vector2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GuardGallivant {

    private static final char OBSTACLE = '#';
    private static final char OBSTRUCTION = 'O';
    private static final char UNVISITED = '.';
    private static final char VISITED = 'X';

    private final char[][] map;
    private final int rows;
    private final int cols;

    private GuardState initialGuardState;
    private Set<GuardState> guardStates = new HashSet<>();

    public GuardGallivant(char[][] inputs) {

        this.map = inputs;
        this.rows = inputs.length;
        this.cols = inputs[0].length;

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(inputs[row][col] == '^') {
                    initialGuardState = new GuardState(new Vector2(col,row), new Vector2(0,-1));
                    guardStates.add(initialGuardState);
                    break;
                }
            }
        }
    }

    public long solveA() {
        simulateGuardMovement();
        return guardStates.stream().map(GuardState::pos).distinct().count();

    }

    public long solveB() {

        // Obtain first execution guard information
        simulateGuardMovement();
        List<Vector2> possibleObstructionPositions =  guardStates.stream().map(GuardState::pos)
                .distinct().filter(pos -> !pos.equals(initialGuardState.pos())).toList();
        resetMap();

        // Search for loops
        long loopCount = 0;
        for(Vector2 obstructionPos : possibleObstructionPositions) {
            map[obstructionPos.getY()][obstructionPos.getX()] = OBSTRUCTION;
            if(simulateGuardMovement()) {
                loopCount++;
            }
            resetMap();
        }

        return loopCount;

    }

    private boolean simulateGuardMovement() {
        boolean isLoop = false;
        Vector2 guardPos = initialGuardState.pos();
        Vector2 guardDir = initialGuardState.dir();

        Vector2 nextPos;

        do {

            nextPos = Vector2.transform(guardPos,guardDir);
            if(canMove(nextPos)) {
                guardPos = nextPos;
                GuardState guardState = new GuardState(guardPos, guardDir);
                if(guardStates.contains(guardState)) {
                    isLoop = true;
                } else {
                    guardStates.add(guardState);
                    map[nextPos.getY()][nextPos.getX()] = VISITED;
                }
            } else {
                guardDir.rotateLeft();
            }

        } while(!isOutOfBounds(nextPos.getY(), nextPos.getX()) && !isLoop);

        return isLoop;
    }

    private boolean canMove(Vector2 nextPos) {
        if(isOutOfBounds(nextPos.getY(), nextPos.getX())) {
            return false;
        }

        return map[nextPos.getY()][nextPos.getX()] != OBSTACLE && map[nextPos.getY()][nextPos.getX()] != OBSTRUCTION;
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= rows || col < 0 || col >= cols;
    }

    private void resetMap() {

        guardStates = new HashSet<>();
        guardStates.add(initialGuardState);

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(map[row][col] != OBSTACLE) {
                    map[row][col] = UNVISITED;
                }
            }
        }
    }
}
