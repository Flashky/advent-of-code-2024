package com.adventofcode.flashk.day13;

import com.adventofcode.flashk.common.Vector2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Machine {

    private static final Pattern BUTTONS_PATTERN = Pattern.compile("X\\+(\\d*), Y\\+(\\d*)");
    private static final Pattern PRIZE_PATTERN = Pattern.compile("X=(\\d*), Y=(\\d*)");

    private static final int TOKENS_A = 3;
    private static final int TOKENS_B = 1;

    private final Vector2 buttonA;
    private final Vector2 buttonB;
    private final Vector2 prizePosition;
    private final long prizePositionBX;
    private final long prizePositionBY;

    public Machine(String buttonA, String buttonB, String prize){
        this.buttonA = initializeVector(buttonA);
        this.buttonB = initializeVector(buttonB);
        this.prizePosition = initializeVector(prize);
        this.prizePositionBX = prizePosition.getX() + 10000000000000L;
        this.prizePositionBY = prizePosition.getY() + 10000000000000L;

    }

    private Vector2 initializeVector(String entry) {
        if(entry.startsWith("Button")) {
            Matcher matcher = BUTTONS_PATTERN.matcher(entry);
            if(matcher.find()){
                int xPos = Integer.parseInt(matcher.group(1));
                int yPos = Integer.parseInt(matcher.group(2));
                return new Vector2(xPos, yPos);
            }
        } else if(entry.startsWith("Prize")) {
            Matcher matcher = PRIZE_PATTERN.matcher(entry);
            if(matcher.find()){
                int xPos = Integer.parseInt(matcher.group(1));
                int yPos = Integer.parseInt(matcher.group(2));
                return new Vector2(xPos, yPos);
            }
        }

        throw new IllegalArgumentException("Invalid entry");
    }

    public long optimize() {

        int minTokens = Integer.MAX_VALUE;

        for(int buttonAPresses = 0; buttonAPresses <= 100; buttonAPresses++) {
            for(int buttonBPresses = 0; buttonBPresses <= 100; buttonBPresses++) {
                Vector2 initialPosition = new Vector2(0, 0);
                Vector2 finalPositionA = new Vector2(buttonAPresses * buttonA.getX(), buttonAPresses * buttonA.getY());
                Vector2 finalPositionB = new Vector2(buttonBPresses * buttonB.getX(), buttonBPresses * buttonB.getY());

                initialPosition.transform(finalPositionA);
                initialPosition.transform(finalPositionB);

                if(initialPosition.equals(prizePosition)) {
                    int tokens = buttonAPresses * TOKENS_A + buttonBPresses * TOKENS_B;
                    minTokens = Math.min(minTokens, tokens);
                }
            }
        }

        return minTokens != Integer.MAX_VALUE ? minTokens : -1;
    }

    public long optimizeB() {

        // Ecuación lineal con dos incógnitas

        long axpy = buttonA.getX() * prizePositionBY;
        long aypx = buttonA.getY() * prizePositionBX;
        long axby = (long) buttonA.getX() * buttonB.getY();
        long aybx = (long) buttonA.getY() * buttonB.getX();

        long b = (axpy - aypx) / (axby - aybx);
        long a = (prizePositionBX - b * buttonB.getX()) / buttonA.getX();

        long posX = a * buttonA.getX() + b * buttonB.getX();
        long posY = a * buttonA.getY() + b * buttonB.getY();

        if(posX == prizePositionBX && posY == prizePositionBY) {
            return a * TOKENS_A + b * TOKENS_B;
        } else {
            return -1;
        }

    }

}
