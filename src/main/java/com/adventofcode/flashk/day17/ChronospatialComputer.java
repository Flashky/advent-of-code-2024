package com.adventofcode.flashk.day17;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import java.util.regex.Pattern;

@Getter
public class ChronospatialComputer {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d*)*");

    private long a;
    private long b;
    private long c;

    private final int[] program;
    private int instructionPointer;
    private StringJoiner outJoiner;
    private final String expectedProgram;

    public ChronospatialComputer(List<String> inputs) {
        a = Long.parseLong(inputs.get(0).substring(12));
        b = Long.parseLong(inputs.get(1).substring(12));
        c = Long.parseLong(inputs.get(2).substring(12));

        expectedProgram = inputs.get(4).replace("Program: ", "");
        program = Arrays.stream(expectedProgram.split(",")).mapToInt(Integer::valueOf).toArray();

    }

    public String solveA() {

        instructionPointer = 0;
        outJoiner = new StringJoiner(",");

        int opcode;
        int operator;

        while(instructionPointer < program.length) {
            opcode = program[instructionPointer];
            operator = program[instructionPointer+1];

            execute(opcode, operator);

            if(opcode != 3) {
                instructionPointer += 2;
            }


        }

        return outJoiner.toString();
    }

    public long solveB() {

        // https://www.reddit.com/r/adventofcode/comments/1hn01ke/2024_day_17_part_2_code_works_until_certain/

        return findRegistryA2(0, StringUtils.EMPTY, StringUtils.EMPTY);

    }

    private long findRegistryA2(int digit, String currentOctalNumber, String output) {

        /*
        if(digit == 11) {
            System.out.printf("oct: %s -> %s", currentOctalNumber, output);
            System.out.println();
        }*/

        if(!expectedProgram.endsWith(output)) {
            return -1;
        } else if(digit == program.length) {
            long result = Long.parseLong(currentOctalNumber, 8);
            return expectedProgram.equals(output) ? result : -1;
        }

        for(int octalDigit = 0; octalDigit < 8; octalDigit++) {
            this.a = Long.parseLong(currentOctalNumber + octalDigit, 8);
            this.b = 0;
            this.c = 0;
            String partialOutput = solveA();
            long result = findRegistryA2(digit+1, currentOctalNumber+octalDigit, partialOutput);
            if(result != -1) {
                return result;
            }
        }

        return -1;
    }

    private void execute(int opcode, int operator) {

        switch(opcode) {
            case 0: adv(operator); break;
            case 1: bxl(operator); break;
            case 2: bst(operator); break;
            case 3: jnz(operator); break;
            case 4: bxc(); break;
            case 5: out(operator); break;
            case 6: bdv(operator); break;
            case 7: cdv(operator); break;
            default: throw new IllegalArgumentException("Invalid opcode: "+opcode);
        }
    }

    private long getComboOperand(int operator) {
        return switch (operator) {
            case 0, 1, 2, 3 -> operator;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    private void adv(int operand) {
        long comboOperand = getComboOperand(operand);
        long power = (long) Math.pow(2, comboOperand);
        a = a / power;
    }

    private void bxl(int operand) {
        b = xor(b, operand);
    }

    private void bst(int operand) {
        long comboOperand = getComboOperand(operand);
        b = comboOperand % 8;
    }

    private void jnz(int operator) {
        if(a != 0) {
            instructionPointer = operator;
        } else {
            instructionPointer += 2;
        }
    }

    private void bxc() {
        b = xor(b, c);
    }

    private void out(int operand) {
        long comboOperand = getComboOperand(operand);
        int mod = (int) comboOperand % 8;
        outJoiner.add(String.valueOf(mod));
    }

    private void bdv(int operand) {
        long comboOperand = getComboOperand(operand);
        b = a / (int) Math.pow(2, comboOperand);
    }

    private void cdv(int operand) {
        long comboOperand = getComboOperand(operand);
        c = a / (int) Math.pow(2, comboOperand);
    }

    private long xor(long a, long b) {
        return a ^ b;
    }


}
