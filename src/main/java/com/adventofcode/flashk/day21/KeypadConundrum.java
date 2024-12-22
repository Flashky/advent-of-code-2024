package com.adventofcode.flashk.day21;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class KeypadConundrum {

    private Numpad numpadRobot = new Numpad();
    private List<DirectionalKeypad> directionalKeypads = new ArrayList<>();

    private List<String> codes;

    public KeypadConundrum(List<String> inputs) {
        codes = inputs;
    }

    public long solveA() {

        long result = 0;
        directionalKeypads.add(new DirectionalKeypad());
        directionalKeypads.add(new DirectionalKeypad());


        for(String code : codes) {
            result += press(code);
        }

        return result;
    }

    private long press(String code) {
        String movements = numpadRobot.press(code);
        String directionalMovements = StringUtils.EMPTY;

        for(DirectionalKeypad directionalKeypad : directionalKeypads) {

            directionalMovements = directionalKeypad.press(movements);
            movements = directionalMovements;
        }

        long shortestSequenceLength = directionalMovements.length();
        long numericValue = Long.parseLong(code.replace("A", StringUtils.EMPTY));

        return shortestSequenceLength * numericValue;
    }
}
