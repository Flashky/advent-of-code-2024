package com.adventofcode.flashk.day19;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinenLayout {

    private List<String> patterns;
    private List<String> designs;

    private Map<String,Boolean> memo = new HashMap<>();

    public LinenLayout(List<String> inputs) {
        patterns = Arrays.stream(inputs.getFirst().replace(StringUtils.SPACE, StringUtils.EMPTY).split(","))
                .sorted(new PatternComparator())
                .toList();
        inputs.removeFirst();
        inputs.removeFirst();
        designs = inputs;
    }

    public int solveA() {
        int result = 0;
        for(String design : designs) {
            if(isPossible(design)) {
                result++;
            }
        }
        return result;
    }

    private boolean isPossible(String design){

        if(memo.containsKey(design)) {
            return memo.get(design);
        }

        if(StringUtils.EMPTY.equals(design)) {
            return true;
        }

        for(String pattern : patterns) {
            int index = design.indexOf(pattern);
            if(index != - 1) {
                String leftSide = design.substring(0, index);
                String rightSide = design.substring(index+pattern.length());

                memo.put(leftSide, isPossible(leftSide));
                memo.put(rightSide, isPossible(rightSide));

                if(memo.get(leftSide) && memo.get(rightSide)) {
                    return true;
                }
            }
        }

        return false;
    }

}
