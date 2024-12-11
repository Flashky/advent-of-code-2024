package com.adventofcode.flashk.day11;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlutonianPebbles {

    private Map<Long, Long> rocksPerNumber;

    public PlutonianPebbles(List<String> inputs) {
        rocksPerNumber = Arrays.stream(inputs.getFirst().split(StringUtils.SPACE)).map(Long::valueOf)
                               .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    public long solve(int blinks) {

        for(int i = 0; i < blinks; i++) {
            blink();
        }

        return rocksPerNumber.values().stream().reduce(0L, Long::sum);
    }

    private void blink() {

        Map<Long,Long> processedRocks = new HashMap<>();

        for(Map.Entry<Long,Long> rock : rocksPerNumber.entrySet()) {
            long number = rock.getKey();
            long rockCount = rock.getValue();
            long digits = String.valueOf(number).length();

            if(number == 0) {
                updateNumber(1L, rockCount, processedRocks);
            } else if(digits % 2 == 0) {
                String strNumber = String.valueOf(number);
                
                String strNumberLeft = strNumber.substring(0, strNumber.length() / 2);
                updateNumber(Long.parseLong(strNumberLeft), rockCount, processedRocks);

                String strNumberRight = strNumber.substring(strNumber.length() / 2);
                updateNumber(Long.parseLong(strNumberRight), rockCount, processedRocks);
            } else {
                updateNumber(number*2024L, rockCount, processedRocks);
            }
        }

        rocksPerNumber = processedRocks;
    }

    private void updateNumber(long number, long rockCount, Map<Long,Long> processedRocks) {
        rockCount += processedRocks.getOrDefault(number, 0L);
        processedRocks.put(number, rockCount);
    }

}
