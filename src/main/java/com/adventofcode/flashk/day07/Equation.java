package com.adventofcode.flashk.day07;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.stream.Collectors;

public class Equation {

    private final long result;
    private final Deque<Integer> operators;
    private boolean concatenate;

    public Equation(String input) {
        String[] inputParts = input.split(": ");
        result = Long.parseLong(inputParts[0]);
        operators = Arrays.stream(inputParts[1].split(StringUtils.SPACE)).map(Integer::valueOf)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public long solve(boolean concatenate) {
        this.concatenate = concatenate;
        return hasSolution(0) ? result : 0;
    }

    private boolean hasSolution(long partialResult) {

        if(operators.isEmpty()) {
            return partialResult == result;
        } else if(partialResult > result) {
            return false;
        }

        Integer currentOperator = operators.poll();
        boolean hasSolution = hasSolution(partialResult + currentOperator);

        if(!hasSolution) {
            hasSolution = hasSolution(partialResult * currentOperator);
        }

        if(!hasSolution && concatenate) {
            long concatenatedPartialResult = Long.parseLong(String.valueOf(partialResult) + currentOperator);
            hasSolution = hasSolution(concatenatedPartialResult);
        }

        operators.addFirst(currentOperator);

        return hasSolution;
    }
}

