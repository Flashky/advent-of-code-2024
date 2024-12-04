package com.adventofcode.flashk.day04;

import com.adventofcode.flashk.common.Array2DUtil;
import com.adventofcode.flashk.common.Vector2;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CeresSearch {

    private static final String XMAS = "XMAS";
    private static final String SAMX = "SAMX";
    private static final String MAS = "MAS";
    private static final char X = 'X';
    private static final char M = 'M';
    private static final char A = 'A';
    private static final char S = 'S';

    private final char[][] input;
    private final int rows;
    private final int cols;
    private final List<Vector2> xPositions = new ArrayList<>();
    private final List<Vector2> aPositions = new ArrayList<>();

    public CeresSearch(char[][] input){
        this.input = input;
        this.rows = input.length;
        this.cols = input[0].length;

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(input[row][col] == X) {
                    xPositions.add(new Vector2(col,row));
                } else if(input[row][col] == A) {
                    aPositions.add(new Vector2(col,row));
                }
            }
        }
    }

    public long solveA() {
        return xPositions.stream().map(this::find).reduce(0, Integer::sum);
    }

    public long solveB() {
        return aPositions.stream().filter(this::hasMasDiagonal1).filter(this::hasMasDiagonal2).count();
    }

    private int find(Vector2 xPos) {
        char[] word = MAS.toCharArray();

        int totalCount = find(word, 0, xPos, new Vector2(-1,-1));
        totalCount += find(word, 0, xPos, new Vector2(-1,1));
        totalCount += find(word, 0, xPos, new Vector2(1,-1));
        totalCount += find(word, 0, xPos, new Vector2(1,1));
        totalCount += find(word, 0, xPos, new Vector2(-1,0));
        totalCount += find(word, 0, xPos, new Vector2(1,0));
        totalCount += find(word, 0, xPos, new Vector2(0,-1));
        totalCount += find(word, 0, xPos, new Vector2(0,1));

        return totalCount;
    }

    private int find(final char[] word, int letterIndex, Vector2 position, final Vector2 direction) {
        Vector2 newPos = Vector2.transform(position, direction);

        if(isOutOfRange(newPos.getY(), newPos.getX())) {
            return 0;
        }

        if(input[newPos.getY()][newPos.getX()] != word[letterIndex]) {
            return 0;
        }

        if(input[newPos.getY()][newPos.getX()] == S && letterIndex == word.length-1) {
            return 1;
        }

        return find(word, letterIndex+1, newPos, direction);
    }

    private boolean hasMasDiagonal1(Vector2 aPos){

        int upLeftRow = aPos.getY()-1;
        int upLeftCol = aPos.getX()-1;

        if(isOutOfRange(upLeftRow, upLeftCol)) {
            return false;
        }

        int downRightRow = aPos.getY()+1;
        int downRightCol = aPos.getX()+1;

        if(isOutOfRange(downRightRow, downRightCol)) {
            return false;
        }

        if(input[upLeftRow][upLeftCol] == M && input[downRightRow][downRightCol] == S){
            return true;
        }

        return input[upLeftRow][upLeftCol] == S && input[downRightRow][downRightCol] == M;
    }

    private boolean hasMasDiagonal2(Vector2 aPos){

        int upRightRow = aPos.getY()-1;
        int upRightCol = aPos.getX()+1;

        if(isOutOfRange(upRightRow, upRightCol)) {
            return false;
        }

        int downLeftRow = aPos.getY()+1;
        int downLeftCol = aPos.getX()-1;

        if(isOutOfRange(downLeftRow, downLeftCol)) {
            return false;
        }

        if(input[upRightRow][upRightCol] == M && input[downLeftRow][downLeftCol] == S){
            return true;
        }

        return input[upRightRow][upRightCol] == S && input[downLeftRow][downLeftCol] == M;
    }

    private boolean isOutOfRange(int row, int col) {
        return row < 0 || row >= rows || col < 0 || col >= cols;
    }

}