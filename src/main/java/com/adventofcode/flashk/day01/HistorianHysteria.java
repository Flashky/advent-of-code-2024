package com.adventofcode.flashk.day01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorianHysteria {

    private List<Long> left = new ArrayList<>();
    private List<Long> right = new ArrayList<>();

    public HistorianHysteria(List<String> input) {
        for(String line : input) {
            String[] split = line.split("   ");
            left.add(Long.valueOf(split[0]));
            right.add(Long.valueOf(split[1]));
        }
    }

    public Long solveA() {
        List<Long> leftSorted = left.stream().sorted().toList();
        List<Long> rightSorted = right.stream().sorted().toList();

        Long result = 0L;

        for(int i  = 0; i < leftSorted.size(); i++) {
            result += Math.abs(leftSorted.get(i)-rightSorted.get(i));
        }

        return result;
    }

    public Long solveB() {

        Long result = 0L;
        Map<Long,Long> numberOccurrences = new HashMap<>();

        for(Long number : left) {
            Long occurrences = 0L;
            if(numberOccurrences.containsKey(number)) {
                occurrences = numberOccurrences.get(number);
                result += number * numberOccurrences.get(number);
            } else {

                for(Long numberRight : right) {
                    if(number.equals(numberRight)) {
                        occurrences++;
                    }
                }

                numberOccurrences.put(number, occurrences);
            }
            result += number * occurrences;

        }

        return result;
    }


}
