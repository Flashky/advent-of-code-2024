package com.adventofcode.flashk.day02;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Report {

    private List<Integer> levels;

    public Report(String input) {
        // Warning: do not use .toList() as it would create an immutable list
        levels = Arrays.stream(input.split(StringUtils.SPACE)).map(Integer::valueOf).collect(Collectors.toList());
    }

    public boolean isSafe() {
        boolean expectedIncreasing = isIncreasing();
        boolean isSafe = true;

        int i = 1;
        int lastLevel = levels.get(0);

        while(isSafe && i < levels.size()) {
            int currentLevel = levels.get(i);
            int distance = currentLevel-lastLevel;

            if(distance == 0) {
                isSafe = false;
            } else if(expectedIncreasing) {
                if(distance > 3 || distance < 0) {
                    isSafe = false;
                }
            } else {
                if(distance < -3 || distance > 0) {
                    isSafe = false;
                }
            }

            lastLevel = currentLevel;
            i++;
        }

        return isSafe;
    }

    public boolean isSafeDampener() {
        if(isSafe()) {
            return true;
        }

        List<Integer> copyLevels = new ArrayList<>(levels);

        boolean isSafe = false;
        int i = 0;

        while(!isSafe && i < levels.size()) {

            levels.remove(i);
            isSafe = isSafe();

            if(!isSafe()) {
                levels = new ArrayList<>(copyLevels);
                i++;
            }
        }

        return isSafe;
    }
    /**
     * true si creciente
     * false si decreciente;
     * @return
     */
    private boolean isIncreasing() {
        int i = 0;
        boolean found = false;
        int distance = 0;
        while(!found && i < levels.size()-1) {
            distance = levels.get(i+1)-levels.get(i);
            if(distance != 0) {
                found = true;
            }
            i++;
        }

        return distance > 0;
    }
}
