package com.adventofcode.flashk.day17;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import java.util.regex.Pattern;

@Getter
public class ChronospatialComputer {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d*)*");

    private int a;
    private int b;
    private int c;

    private int[] program;
    private int instructionPointer;
    private StringJoiner outJoiner;
    private boolean allowReservedOperand;

    public ChronospatialComputer(List<String> inputs) {
        a = Integer.parseInt(inputs.get(0).substring(12));
        b = Integer.parseInt(inputs.get(1).substring(12));
        c = Integer.parseInt(inputs.get(2).substring(12));

        String programValues = inputs.get(4).replace("Program: ", "");
        program = Arrays.stream(programValues.split(",")).mapToInt(Integer::valueOf).toArray();

    }

    public String solveA(boolean allowReservedOperand) {

        instructionPointer = 0;
        outJoiner = new StringJoiner(",");
        this.allowReservedOperand  = allowReservedOperand;

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

    private void execute(int opcode, int operator) {

        switch(opcode) {
            case 0: adv(operator); break;
            case 1: bxl(operator); break;
            case 2: bst(operator); break;
            case 3: jnz(operator); break;
            case 4: bxc(operator); break;
            case 5: out(operator); break;
            case 6: bdv(operator); break;
            case 7: cdv(operator); break;
            default: throw new IllegalArgumentException("Invalid opcode: "+opcode);
        }
    }

    private int getComboOperand(int operator) {
        switch(operator) {
            case 0,1,2,3:
                return operator;
            case 4:
                return a;
            case 5:
                return b;
            case 6:
                return c;
            default:
                if (allowReservedOperand) {
                    return operator;
                }
                throw new IllegalArgumentException("Invalid operator: "+operator);
        }
    }

    private void adv(int operand) {
        int comboOperand = getComboOperand(operand);
        int power = (int) Math.pow(2, comboOperand);
        a = a / power;

        // TODO es posible que haya que truncar.
    }

    private void bxl(int operand) {
        b = xor(b, operand);
    }

    private void bst(int operand) {
        int comboOperand = getComboOperand(operand);
        b = comboOperand % 8;
    }

    private void jnz(int operator) {
        if(a != 0) {
            instructionPointer = operator;
        } else {
            instructionPointer += 2;
        }
    }

    private void bxc(int operand) {
        // https://en.wikipedia.org/wiki/Bitwise_operation#XOR
        b = xor(b, c);
    }

    private void out(int operand) {
        int comboOperand = getComboOperand(operand);
        int mod = comboOperand % 8;
        outJoiner.add(String.valueOf(mod));
    }

    private void bdv(int operand) {
        int comboOperand = getComboOperand(operand);
        b = a / (int) Math.pow(2, comboOperand);
    }

    private void cdv(int operand) {
        int comboOperand = getComboOperand(operand);
        c = a / (int) Math.pow(2, comboOperand);
    }

    private int xor(int a, int b) {
        return a ^ b;
    }


}
