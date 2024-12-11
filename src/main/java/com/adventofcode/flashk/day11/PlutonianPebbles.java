package com.adventofcode.flashk.day11;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlutonianPebbles {

    private Map<Long, Long> rocksPerNumber;

    public PlutonianPebbles(List<String> inputs) {
        rocksPerNumber = Arrays.stream(inputs.get(0).split(StringUtils.SPACE)).map(Long::valueOf)
                               .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    public long solve(int blinks) {

        for(int i = 0; i < blinks; i++) {
            blink();
        }

        return countRocks();
    }

    private void blink() {

        Map<Long,Long> processedRocks = new HashMap<>();

        for(Long number : rocksPerNumber.keySet()) {
            long digits = String.valueOf(number).length();
            long rockCount = rocksPerNumber.get(number);
            long newNumber;
            if(number == 0) {
                newNumber = 1L;
                rockCount += processedRocks.getOrDefault(newNumber, 0L);
                processedRocks.put(1L, rockCount);
            } else if(digits % 2 == 0) {
                String strNumber = String.valueOf(number);

                String strNumberLeft = strNumber.substring(0, strNumber.length() / 2);
                newNumber = Long.parseLong(strNumberLeft);
                long rockCountLeft = rockCount + processedRocks.getOrDefault(newNumber, 0L);
                processedRocks.put(newNumber, rockCountLeft);

                String strNumberRight = strNumber.substring(strNumber.length() / 2);
                newNumber = Long.parseLong(strNumberRight);
                long rockCountRight = rockCount + processedRocks.getOrDefault(newNumber, 0L);
                processedRocks.put(newNumber, rockCountRight);

            } else {
                newNumber = number*2024L;
                rockCount += processedRocks.getOrDefault(newNumber, 0L);
                processedRocks.put(newNumber, rockCount);
            }
        }

        rocksPerNumber = processedRocks;
    }

    private long countRocks() {
        long result = 0;
        for(Long number : rocksPerNumber.keySet()) {
            result += rocksPerNumber.get(number);
        }
        return result;
    }

}
