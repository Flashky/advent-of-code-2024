package com.adventofcode.flashk.day07;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.stream.Collectors;

public class Equation {

    private final long result;
    private final Deque<Integer> numbers;
    private boolean concatenate;

    public Equation(String input) {
        String[] inputParts = input.split(": ");
        result = Long.parseLong(inputParts[0]);
        numbers = Arrays.stream(inputParts[1].split(StringUtils.SPACE)).map(Integer::valueOf)
                        .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public long solve(boolean concatenate) {
        this.concatenate = concatenate;
        return hasSolution(0) ? result : 0;
    }

    private boolean hasSolution(long partialResult) {

        if(numbers.isEmpty()) {
            return partialResult == result;
        } else if(partialResult > result) {
            return false;
        }

        Integer number = numbers.poll();
        boolean hasSolution = hasSolution(partialResult + number);

        if(!hasSolution) {
            hasSolution = hasSolution(partialResult * number);
        }

        if(!hasSolution && concatenate) {
            long concatenatedPartialResult = Long.parseLong(String.valueOf(partialResult) + number);
            hasSolution = hasSolution(concatenatedPartialResult);
        }

        numbers.addFirst(number);

        return hasSolution;
    }
}

