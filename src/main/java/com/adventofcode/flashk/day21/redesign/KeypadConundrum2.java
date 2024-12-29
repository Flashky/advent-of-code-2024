package com.adventofcode.flashk.day21.redesign;



import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class KeypadConundrum2 {

    private final List<String> codes;

    private final Keypad numpad;

    public KeypadConundrum2(List<String> inputs, int keypadsNumber) {
        codes = inputs;

        // Create all keypad robots and link between them
        Keypad previousRobot = null;

        for(int i = 0; i < keypadsNumber; i++) {
            Keypad currentRobot = new Keypad(true);
            if(previousRobot != null) {
                currentRobot.setNextKeypad(previousRobot);
            }
            previousRobot = currentRobot;
        }

        numpad = new Keypad(false);
        numpad.setNextKeypad(previousRobot);
    }

    public long solveA() {

        long result = 0;
        for(String code : codes) {
            result += press(code);
        }

        return result;
    }

    private long press(String code) {
        long shortestSequenceLength = numpad.press(code);
        long numericValue = Long.parseLong(code.replace("A", StringUtils.EMPTY));
        return shortestSequenceLength * numericValue;
    }


}
