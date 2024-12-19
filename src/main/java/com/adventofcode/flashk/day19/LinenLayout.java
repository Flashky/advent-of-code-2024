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
    private Map<String,Integer> memo2 = new HashMap<>();

    public LinenLayout(List<String> inputs) {
        patterns = Arrays.stream(inputs.getFirst().replace(StringUtils.SPACE, StringUtils.EMPTY).split(","))
                .sorted(new PatternComparator())
                .toList().reversed();
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

    public int solveB() {
        int result = 0;
        for(String design : designs) {
            isPossible2(design, design);
        }
        return 0;
    }

    /*
    private int countPossible(String design) {
        if(memo2.containsKey(design)) {
            memo2.put(design, memo2.get(design)+1);
        }

        if(StringUtils.EMPTY.equals(design)) {
            return 1;
        }

        for(String pattern : patterns) {
            int index = design.indexOf(pattern);

            String leftSide = design.substring(0, index);
            String rightSide = design.substring(index+pattern.length());
            memo.put(leftSide, isPossible(leftSide));
            memo.put(rightSide, isPossible(rightSide));
        }
    }*/

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

    private boolean isPossible2(String targetDesign, String design){

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

                memo.put(leftSide, isPossible2(targetDesign, leftSide));
                memo.put(rightSide, isPossible2(targetDesign, rightSide));

                if(memo.get(leftSide) && memo.get(rightSide)) {
                    int count = memo2.getOrDefault(targetDesign, 0);
                    memo2.put(targetDesign, count+1);
                }
            }
        }

        return false;
    }

}
