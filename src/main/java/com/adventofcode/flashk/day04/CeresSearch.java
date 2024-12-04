package com.adventofcode.flashk.day04;

import com.adventofcode.flashk.common.Array2DUtil;
import com.adventofcode.flashk.common.Vector2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class CeresSearch {

    private static final String XMAS = "XMAS";
    private static final String SAMX = "SAMX";
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
        long wordCount = countHorizontal();
        wordCount += countVertical();
        wordCount += countDiagonal();

        return wordCount;
    }

    public long solveB() {
       return aPositions.stream().filter(this::hasMasDiagonal1).filter(this::hasMasDiagonal2).count();
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

    private int countHorizontal() {
        int totalCount = 0;
        for(int row = 0; row < rows; row++) {
            String line = new String(input[row]);
            totalCount += StringUtils.countMatches(line, XMAS) + StringUtils.countMatches(line, SAMX);
        }
        return totalCount;
    }

    private int countVertical() {

        char[][] transposedInput = Array2DUtil.transpose(input);
        int totalCount = 0;
        for(int row = 0; row < rows; row++) {
            String line = new String(transposedInput[row]);
            totalCount += StringUtils.countMatches(line, XMAS) + StringUtils.countMatches(line, SAMX);
        }
        return totalCount;
    }

    private long countDiagonal() {
        int totalCount = 0;
        for(Vector2 xPosition : xPositions) {
            totalCount += upLeftDiagonal(xPosition.getY(), xPosition.getX());
            totalCount += upRightDiagonal(xPosition.getY(), xPosition.getX());
            totalCount += downLeftDiagonal(xPosition.getY(), xPosition.getX());
            totalCount += downRightDiagonal(xPosition.getY(), xPosition.getX());
        }
        
        return totalCount;
    }


    private int upLeftDiagonal(int row, int col) {

        int nextPosRow = row-1;
        int nextPosCol = col-1;

        if(isOutOfRange(nextPosRow, nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != M) {
            return 0;
        }

        if(isOutOfRange(--nextPosRow, --nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != A) {
            return 0;
        }

        if(isOutOfRange(--nextPosRow, --nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != S) {
            return 0;
        }

        return 1;
    }

    private int upRightDiagonal(int row, int col) {

        int nextPosRow = row-1;
        int nextPosCol = col+1;

        if(isOutOfRange(nextPosRow, nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != M) {
            return 0;
        }

        if(isOutOfRange(--nextPosRow, ++nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != A) {
            return 0;
        }

        if(isOutOfRange(--nextPosRow, ++nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != S) {
            return 0;
        }

        return 1;
    }

    private int downLeftDiagonal(int row, int col) {
        int nextPosRow = row+1;
        int nextPosCol = col-1;

        if(isOutOfRange(nextPosRow, nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != M) {
            return 0;
        }

        if(isOutOfRange(++nextPosRow, --nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != A) {
            return 0;
        }

        if(isOutOfRange(++nextPosRow, --nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != S) {
            return 0;
        }

        return 1;
    }

    private int downRightDiagonal(int row, int col) {
        int nextPosRow = row+1;
        int nextPosCol = col+1;

        if(isOutOfRange(nextPosRow, nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != M) {
            return 0;
        }

        if(isOutOfRange(++nextPosRow, ++nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != A) {
            return 0;
        }

        if(isOutOfRange(++nextPosRow, ++nextPosCol)) {
            return 0;
        }

        if(input[nextPosRow][nextPosCol] != S) {
            return 0;
        }

        return 1;
    }

    private boolean isOutOfRange(int row, int col) {
        return row < 0 || row >= rows || col < 0 || col >= cols;
    }

}
