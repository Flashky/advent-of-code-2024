package com.adventofcode.flashk.day07;

import lombok.Getter;

import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.stream.Collectors;

public class Equation {

    private final long result;
    private final Deque<Integer> operators;
    private boolean concatenate;

    public Equation(String input) {
        String[] inputParts = input.split(":");
        result = Long.parseLong(inputParts[0]);
        operators = Arrays.stream(inputParts[1].split(" "))
                .skip(1).map(Integer::parseInt).collect(Collectors.toCollection(ArrayDeque::new));
    }

    public long solve(boolean concatenate) {
        this.concatenate = concatenate;
        return hasSolution(operators, 0) ? result : 0;
    }

    private boolean hasSolution(Deque<Integer> operators, long partialResult) {

        if(operators.isEmpty()) {
            return partialResult == result;
        }

        Integer currentOperator = operators.poll();
        boolean hasSolution = hasSolution(operators, partialResult + currentOperator);

        if(!hasSolution && partialResult != 0) {
            hasSolution = hasSolution(operators, partialResult * currentOperator);
        } else if(partialResult == 0) {
            hasSolution = hasSolution(operators, currentOperator);
        }

        if(!hasSolution && concatenate) {
            StringBuilder sb = new StringBuilder();

            if(partialResult != 0) {
                sb.append(partialResult);
            }

            long concatenatedPartialResult = Long.parseLong(sb.append(currentOperator).toString());
            hasSolution = hasSolution(operators, concatenatedPartialResult);

        }

        operators.addFirst(currentOperator);

        return hasSolution;
    }
}

