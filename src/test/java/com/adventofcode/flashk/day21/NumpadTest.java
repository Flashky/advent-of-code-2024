package com.adventofcode.flashk.day21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumpadTest {

    @Test
    void pressTest() {

        Numpad numpad = new Numpad();

        assertEquals(0, numpad.press("A").length());

        numpad.reset();
        assertEquals(1, numpad.press("0").length());

        numpad.reset();
        assertEquals(2, numpad.press("2").length());

        numpad.reset();
        assertEquals(1, numpad.press("3").length());

        numpad.reset();
        assertEquals(4, numpad.press("4").length());

        numpad.reset();
        assertEquals(3, numpad.press("5").length());

        numpad.reset();
        assertEquals(2, numpad.press("6").length());

        numpad.reset();
        assertEquals(5, numpad.press("7").length());

        numpad.reset();
        assertEquals(4, numpad.press("8").length());

        numpad.reset();
        assertEquals(3, numpad.press("9").length());
    }
}