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

    private Deque<Long> rocks;

    private Map<Long, Long> rocksPerNumber;

    public PlutonianPebbles(List<String> inputs) {
        rocks = Arrays.stream(inputs.get(0).split(StringUtils.SPACE)).map(Long::valueOf).collect(Collectors.toCollection(ArrayDeque::new));

        rocksPerNumber = rocks.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public long solveA(int blinks) {
        for(int i = 0; i < blinks; i++) {
            blink();
        }

        return rocks.size();
    }

    public long solveB(int blinks) {
        for(int i = 0; i < blinks; i++) {
            blinkB();
        }
        return countRocksInMap();
    }

    private void blinkB() {

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

    private long countRocksInMap() {
        long result = 0;
        for(Long number : rocksPerNumber.keySet()) {
            result += rocksPerNumber.get(number);
        }
        return result;
    }

    private void blink() {

        Deque<Long> processedRocks = new ArrayDeque<>();

        // Para cada roca:
        // Si roca == 0 -> roca = 1
        // y si roca tiene un número par de dígitos -> Partir en 2 -> tendré que convertir a string y partir.
        // resto de casos: roca *= 2024

        while(!rocks.isEmpty()) {
            long number = rocks.pollFirst();
            long digits = String.valueOf(number).length();
            if(number == 0) {
                processedRocks.add(1L);
            } else if(digits % 2 == 0) {
                String strNumber = String.valueOf(number);
                String numberLeft = strNumber.substring(0, strNumber.length() / 2);
                String numberRight = strNumber.substring(strNumber.length() / 2);
                processedRocks.add(Long.valueOf(numberLeft));
                processedRocks.add(Long.valueOf(numberRight));
            } else {
                processedRocks.add(number*2024);
            }
        }


        rocks = processedRocks;
    }
}
