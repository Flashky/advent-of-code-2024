package com.adventofcode.flashk.day21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionalKeypadTest {


    @Test
    void pressFromAcceptTest() {

        DirectionalKeypad keypad = new DirectionalKeypad();

        assertEquals("v<<A", keypad.press("<"));

        keypad.setCurrentKey(DirectionalKeypad.ACCEPT);
        assertEquals("vA", keypad.press(">"));

        keypad.setCurrentKey(DirectionalKeypad.ACCEPT);
        assertEquals("<A", keypad.press("^"));

        keypad.setCurrentKey(DirectionalKeypad.ACCEPT);
        assertEquals("<vA", keypad.press("v"));

    }

    @Test
    void pressFromUpTest() {

        DirectionalKeypad keypad = new DirectionalKeypad();

        keypad.setCurrentKey(DirectionalKeypad.UP);
        assertEquals(">A", keypad.press("A"));

        keypad.setCurrentKey(DirectionalKeypad.UP);
        assertEquals("v<A", keypad.press("<"));

        keypad.setCurrentKey(DirectionalKeypad.UP);
        assertEquals("v>A", keypad.press(">"));

        keypad.setCurrentKey(DirectionalKeypad.UP);
        assertEquals("vA", keypad.press("v"));

    }

    @Test
    void pressFromLeftTest() {

        DirectionalKeypad keypad = new DirectionalKeypad();

        keypad.setCurrentKey(DirectionalKeypad.LEFT);
        assertEquals("^>>A", keypad.press("A"));

        keypad.setCurrentKey(DirectionalKeypad.LEFT);
        assertEquals(">>A", keypad.press(">"));

        keypad.setCurrentKey(DirectionalKeypad.LEFT);
        assertEquals(">^A", keypad.press("^"));

        keypad.setCurrentKey(DirectionalKeypad.LEFT);
        assertEquals(">A", keypad.press("v"));

    }

    @Test
    void pressFromDownToATest() {

        DirectionalKeypad keypad = new DirectionalKeypad();

        keypad.setCurrentKey(DirectionalKeypad.DOWN);
        assertEquals(">^A", keypad.press("A"));

        keypad.setCurrentKey(DirectionalKeypad.DOWN);
        assertEquals("<A", keypad.press("<"));

        keypad.setCurrentKey(DirectionalKeypad.DOWN);
        assertEquals(">A", keypad.press(">"));

        keypad.setCurrentKey(DirectionalKeypad.DOWN);
        assertEquals("^A", keypad.press("^"));
    }

    @Test
    void pressFromRightTest() {

        DirectionalKeypad keypad = new DirectionalKeypad();

        keypad.setCurrentKey(DirectionalKeypad.RIGHT);
        assertEquals("^A", keypad.press("A"));

        keypad.setCurrentKey(DirectionalKeypad.RIGHT);
        assertEquals("<<A", keypad.press("<"));

        keypad.setCurrentKey(DirectionalKeypad.RIGHT);
        assertEquals("<^A", keypad.press("^"));

        keypad.setCurrentKey(DirectionalKeypad.RIGHT);
        assertEquals("<A", keypad.press("v"));


    }





}