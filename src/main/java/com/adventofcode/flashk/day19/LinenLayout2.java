package com.adventofcode.flashk.day19;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinenLayout2 {

    private List<String> patterns;
    private List<String> designs;

    private Map<MemoState,Long> memo = new HashMap<>();

    public LinenLayout2(List<String> inputs) {
        patterns = Arrays.stream(inputs.getFirst().replace(StringUtils.SPACE, StringUtils.EMPTY).split(","))
                         .sorted(new PatternComparator())
                         .toList().reversed();
        inputs.removeFirst();
        inputs.removeFirst();
        designs = inputs;
    }

    public long solveB() {
        long result = 0;
        for(String design : designs) {
            result += count(design, StringUtils.EMPTY);
            memo.clear();
        }
        return result;
    }

    private long count(String design, String pattern){

        if(design.equals(pattern)) {
            return 1L;
        }

        if(!design.startsWith(pattern)) {
            return 0;
        }

        long count = 0;
        String rightSide = design.replaceFirst(pattern, StringUtils.EMPTY);

        for(String newPattern : patterns) {

            MemoState newState = new MemoState(rightSide, newPattern);

            long partialCount;
            if(memo.containsKey(newState)) {
                partialCount = memo.get(newState);
            } else {
                partialCount = count(rightSide, newPattern);
                memo.put(new MemoState(rightSide, newPattern), partialCount);
            }

            count += partialCount;
        }

        return count;
    }

}
