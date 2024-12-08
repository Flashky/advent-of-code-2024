package com.adventofcode.flashk.day08;

import com.adventofcode.flashk.common.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResonantCollinearity {

    private static final char EMPTY = '.';
    private char[][] map;
    private char[][] antinodeMap;
    private final int rows;
    private final int cols;
    private final Map<Character, List<Vector2>> antennasPerFrequency = new HashMap<>();

    public ResonantCollinearity(char[][] input) {

        rows = input.length;
        cols = input[0].length;

        map = input;

        antinodeMap = new char[rows][cols];

        for(int row = 0; row < rows; row++){

            for(int col = 0; col < cols; col++) {
                if(input[row][col] != EMPTY) {
                    char frequency = input[row][col];
                    List<Vector2> antennaPositions = antennasPerFrequency.getOrDefault(frequency, new ArrayList<>());
                    antennaPositions.add(new Vector2(col, row));
                    antennasPerFrequency.putIfAbsent(frequency, antennaPositions);
                }

                antinodeMap[row][col] = EMPTY;
            }
        }

    }

    public long solveA() {

        Set<Vector2> antinodeLocations = new HashSet<>();

        for(Character frequency : antennasPerFrequency.keySet()) {
            List<Vector2> antennas = antennasPerFrequency.get(frequency);

            for(int i = 0; i < antennas.size(); i++) {
                for(int j = 1; j < antennas.size(); j++) {
                    Vector2 antenna1 = antennas.get(i);
                    Vector2 antenna2 = antennas.get(j);

                    if(antenna1.equals(antenna2)) {
                        continue;
                    }

                    Vector2 direction1 = Vector2.direction(antenna1, antenna2);
                    Vector2 direction2 = Vector2.direction(antenna2, antenna1);

                    Vector2 antinode1 = Vector2.transform(antenna1, direction1);
                    Vector2 antinode2 = Vector2.transform(antenna2, direction2);

                    if(!isOutOfBounds(antinode1)) {
                        antinodeLocations.add(antinode1);
                        antinodeMap[antinode1.getY()][antinode1.getX()] = '#';
                    }

                    if(!isOutOfBounds(antinode2)){
                        antinodeLocations.add(antinode2);
                        antinodeMap[antinode2.getY()][antinode2.getX()] = '#';
                    }
                }
            }
        }

        //paint(antinodeMap);
        return antinodeLocations.size();
    }

    public long solveB() {
        Set<Vector2> antinodeLocations = new HashSet<>();

        for(Character frequency : antennasPerFrequency.keySet()) {
            List<Vector2> antennas = antennasPerFrequency.get(frequency);
            antinodeLocations.addAll(antennas);
            for(int i = 0; i < antennas.size(); i++) {
                for(int j = 1; j < antennas.size(); j++) {
                    Vector2 antenna1 = antennas.get(i);
                    Vector2 antenna2 = antennas.get(j);

                    if(antenna1.equals(antenna2)) {
                        continue;
                    }


                    Vector2 direction1 = Vector2.direction(antenna1, antenna2);
                    Vector2 antinode1 = new Vector2(antenna1);
                    do {
                        antinode1.transform(direction1);

                        if (!isOutOfBounds(antinode1)) {
                            antinodeLocations.add(new Vector2(antinode1));
                            antinodeMap[antinode1.getY()][antinode1.getX()] = '#';
                        }
                    } while(!isOutOfBounds(antinode1));

                    Vector2 direction2 = Vector2.direction(antenna2, antenna1);
                    Vector2 antinode2 = new Vector2(antenna2);
                    do {
                        antinode2.transform(direction2);

                        if(!isOutOfBounds(antinode2)) {
                            antinodeLocations.add(new Vector2(antinode2));
                            antinodeMap[antinode2.getY()][antinode2.getX()] = '#';
                        }
                    }while(!isOutOfBounds(antinode2));
                }
            }
        }

        //paint(antinodeMap);

        return antinodeLocations.size();
    }

    private boolean isOutOfBounds(Vector2 antinodePos) {
        return antinodePos.getY() < 0 || antinodePos.getY() >= rows || antinodePos.getX() < 0 || antinodePos.getX() >= cols;
    }

    private void paint(char[][] map) {

        int rows = map.length;
        int cols = map[0].length;

        System.out.println();
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                System.out.print(map[row][col]);
            }
            System.out.println();
        }
        System.out.println();

    }

}
