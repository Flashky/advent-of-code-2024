package com.adventofcode.flashk.day01;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class HistorianHysteria {

    private final List<Long> left = new ArrayList<>();
    private final List<Long> right = new ArrayList<>();

    public HistorianHysteria(List<String> input) {
        for(String line : input) {
            String[] split = line.split(" {3}");
            left.add(Long.valueOf(split[0]));
            right.add(Long.valueOf(split[1]));
        }
    }

    public Long solveA() {
        List<Long> leftSorted = left.stream().sorted().toList();
        List<Long> rightSorted = right.stream().sorted().toList();

        long result = 0L;

        for(int i  = 0; i < leftSorted.size(); i++) {
            result += Math.abs(leftSorted.get(i)-rightSorted.get(i));
        }

        return result;
    }

    public Long solveB() {

        long result = 0L;

        Map<Long,Long> occurrences = right.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for(long number : left) {
            result += number * occurrences.getOrDefault(number,0L);
        }

        return result;
    }

}
