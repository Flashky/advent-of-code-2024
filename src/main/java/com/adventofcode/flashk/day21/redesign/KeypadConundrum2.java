package com.adventofcode.flashk.day21.redesign;



import org.apache.commons.lang3.StringUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Set;

public class KeypadConundrum2 {

    private final List<String> codes;
    private final Deque<Keypad> keypads = new ArrayDeque<>();

    public KeypadConundrum2(List<String> inputs, int keypadsNumber) {
        codes = inputs;
        keypads.add(new Keypad(false));
        for(int i = 0; i < keypadsNumber; i++) {
            keypads.add(new Keypad(true));
        }
    }

    public long solveA() {

        long result = 0;
        for(String code : codes) {
            result += press(code);
        }

        return result;
    }

    private long press(String code) {
        long shortestSequenceLength = pressCode(code);
        long numericValue = Long.parseLong(code.replace("A", StringUtils.EMPTY));
        return shortestSequenceLength * numericValue;
    }

    private long pressCode(String code) {

        if(keypads.isEmpty()) {
            return code.length();
        }

        long shortestSequence = Long.MAX_VALUE;

        Keypad nextKeypad = keypads.pollFirst();

        Set<String> keyPressesList = nextKeypad.press(code);
        for(String keyPresses : keyPressesList) {
            long sequence = pressCode(keyPresses);
            shortestSequence = Math.min(shortestSequence, sequence);
        }

        keypads.addFirst(nextKeypad);

        return shortestSequence;
    }

}
