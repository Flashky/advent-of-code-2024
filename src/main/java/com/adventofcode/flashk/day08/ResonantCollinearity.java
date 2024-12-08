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
    private final int rows;
    private final int cols;
    private final Map<Character, List<Vector2>> antennasPerFrequency = new HashMap<>();

    public ResonantCollinearity(char[][] input) {

        rows = input.length;
        cols = input[0].length;

        for(int row = 0; row < rows; row++){

            for(int col = 0; col < cols; col++) {
                if(input[row][col] != EMPTY) {
                    char frequency = input[row][col];
                    List<Vector2> antennaPositions = antennasPerFrequency.getOrDefault(frequency, new ArrayList<>());
                    antennaPositions.add(new Vector2(col, row));
                    antennasPerFrequency.putIfAbsent(frequency, antennaPositions);
                }

            }
        }

    }

    public int solve(boolean countHarmonics) {
        Set<Vector2> antinodeLocations = new HashSet<>();

        for(Map.Entry<Character,List<Vector2>> frequency : antennasPerFrequency.entrySet()) {
            
            List<Vector2> antennas = frequency.getValue();

            for (int i = 0; i < antennas.size(); i++) {
                for (int j = 1; j < antennas.size(); j++) {
                    Vector2 antenna1 = antennas.get(i);
                    Vector2 antenna2 = antennas.get(j);

                    if (antenna1.equals(antenna2)) {
                        continue;
                    }

                    findAntinodes(countHarmonics, antenna1, antenna2, antinodeLocations);
                    findAntinodes(countHarmonics, antenna2, antenna1, antinodeLocations);

                }
            }
        }

        return antinodeLocations.size();
    }

    private void findAntinodes(boolean countHarmonics, Vector2 antennaA, Vector2 antennaB, Set<Vector2> antinodes) {
        Vector2 direction = Vector2.direction(antennaA, antennaB);
        Vector2 antinode = new Vector2(antennaA);
        do {
            antinode.transform(direction);

            if (isInbounds(antinode)) {
                antinodes.add(new Vector2(antinode));
            }
        } while(isInbounds(antinode) && countHarmonics);

        if(countHarmonics) {
            antinodes.add(antennaA);
        }
    }

    private boolean isInbounds(Vector2 antinodePos) {
        return antinodePos.getY() >= 0 && antinodePos.getY() < rows && antinodePos.getX() >= 0 && antinodePos.getX() < cols;
    }

}
