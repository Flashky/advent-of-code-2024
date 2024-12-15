package com.adventofcode.flashk.common;

public class Array2DUtil {

    private Array2DUtil() {}

    /**
     * Transposes the given 2D array.
     * @param array the array to transpose.
     * @return a new 2D array that is the transpose of the given array.
     */
    public static char[][] transpose(char[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        char[][] transposedArray = new char[cols][rows];
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                transposedArray[col][row] = array[row][col];
            }
        }
        return transposedArray;
    }

    /**
     * Paints the given array.
     * @param map the array to paint
     */
    public static void paint(char[][] map) {

        System.out.println();
        for (char[] chars : map) {
            System.out.println(chars);
        }
        System.out.println();

    }
}
