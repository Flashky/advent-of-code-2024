package com.adventofcode.flashk.day19;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinenLayout2 {

    private List<String> patterns;
    private List<String> designs;

    private Map<String,Boolean> memo = new HashMap<>();

    public LinenLayout2(List<String> inputs) {
        patterns = Arrays.stream(inputs.getFirst().replace(StringUtils.SPACE, StringUtils.EMPTY).split(","))
                         .sorted(new PatternComparator())
                         .toList().reversed();
        inputs.removeFirst();
        inputs.removeFirst();
        designs = inputs;
    }

    public int solveB() {
        int result = 0;
        for(String design : designs) {
            result += count(design, StringUtils.EMPTY);
        }
        return result;
    }

    private int count(String design, String pattern){

        if(design.equals(pattern)) {
            return 1;
        }

        if(!design.startsWith(pattern)) {
            return 0;
        }

        int count = 0;
        String rightSide = design.replaceFirst(pattern, StringUtils.EMPTY);

        for(String newPattern : patterns) {
            count += count(rightSide, newPattern);
        }

        return count;
    }

}
