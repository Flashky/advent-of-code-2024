package com.adventofcode.flashk.day17;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.StringJoiner;

/// Represents the input.data program.
/// Important: it can only represent that program.
public class Program {

    private long a;
    private long b;
    private long c;
    private final String expectedProgram;

    public Program(List<String> inputs) {
        a = Integer.parseInt(inputs.get(0).substring(12));
        b = Integer.parseInt(inputs.get(1).substring(12));
        c = Integer.parseInt(inputs.get(2).substring(12));

        expectedProgram = inputs.get(4).replace("Program: ", "");
    }

    public long solveB() {
        return findRegistryA(StringUtils.EMPTY, StringUtils.EMPTY);
    }

    public String execute() {
        return run();
    }

    private String execute(long a) {
        this.a = a;
        this.b = 0;
        this.c = 0;
        return run();
    }

    private String run() {

        StringJoiner joiner = new StringJoiner(",");

        while(a != 0) {
            b = a % 8; // Se guarda en b 0-7, es decir, un número de 3 bits
            b = b ^ 3; // bitwise xor entre lo que valga b y "011"
            c = a / (long) Math.pow(2,b);
            b = b ^ 5; // bitwise xor entre lo que valga b y "101"
            a /= 8;
            b ^= c;
            joiner.add(String.valueOf(b % 8));
        }

        return joiner.toString();
    }

    private long findRegistryA(String currentOctalNumber, String output) {

        if(!expectedProgram.endsWith(output)) {
            return -1;
        } else if(expectedProgram.equals(output)){
            return Long.parseLong(currentOctalNumber, 8);
        } else if(currentOctalNumber.length() == 16) {
            return -1;
        }

        for(int octalDigit = 0; octalDigit < 8; octalDigit++) {
            long numberA = Long.parseLong(currentOctalNumber + octalDigit, 8);

            String partialOutput = execute(numberA);
            long result = findRegistryA(currentOctalNumber+octalDigit, partialOutput);
            if(result != -1) {
                return result;
            }
        }

        return -1;
    }
}
