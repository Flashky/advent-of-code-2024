package com.adventofcode.flashk.day17;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import java.util.regex.Pattern;

@Getter
public class ChronospatialComputer {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d*)*");

    private int A;
    private int B;
    private int C;

    private int[] program;
    private int instructionPointer;
    private StringJoiner outJoiner;

    public ChronospatialComputer(List<String> inputs) {
        A = Integer.parseInt(inputs.get(0).substring(12));
        B = Integer.parseInt(inputs.get(1).substring(12));
        C = Integer.parseInt(inputs.get(2).substring(12));

        String programValues = inputs.get(4).replace("Program: ", "");
        program = Arrays.stream(programValues.split(",")).mapToInt(Integer::valueOf).toArray();

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

    private void execute(int opcode, int operator) {
        int operand = getOperand(operator);
        switch(opcode) {
            case 0: adv(operand); break;
            case 1: bxl(operand); break;
            case 2: bst(operand); break;
            case 3: jnz(operand); break;
            case 4: bxc(operand); break;
            case 5: out(operand); break;
            case 6: bdv(operand); break;
            case 7: cdv(operand); break;
            default: throw new IllegalArgumentException("Invalid opcode: "+opcode);
        }
    }

    private int getOperand(int operator) {
        switch(operator) {
            case 0,1,2,3:
                return operator;
            case 4:
                return A;
            case 5:
                return B;
            case 6:
                return C;
            default:
                throw new IllegalArgumentException("Operand 7 is not a valid value");
        }
    }

    private void adv(int operator) {
        int power = (int) Math.pow(2, operator);
        A = A / power;

        // TODO es posible que haya que truncar.
    }

    private void bxl(int operator) {
        // TODO B = B XOR combo operator
        // https://en.wikipedia.org/wiki/Bitwise_operation#XOR

        // Steps:
        // Convertir B de octal a binario.
        // Convertir operator de octal a binario
        // Hacer  (B binario) XOR (operator binario) para cada bit.
        // Reconvertir de binario a octal.

        B = xor(B, operator);

    }

    private void bst(int operator) {
        B = operator % 8;
    }

    private void jnz(int operator) {
        if(A != 0) {
            instructionPointer = operator;
        }
    }

    private void bxc(int operator) {
        // TODO B = B XOR C
        // https://en.wikipedia.org/wiki/Bitwise_operation#XOR

        B = xor(B, C);
    }

    private void out(int operator) {
        int mod = operator % 8;
        outJoiner.add(String.valueOf(mod));
    }

    private void bdv(int operator) {
        B = A / (int) Math.pow(2, operator);

        // TODO es posible que haya que truncar.
    }

    private void cdv(int operand) {
        C = A / (int) Math.pow(2, operand);

        // TODO es posible que haya que truncar.
    }

    private int xor(int a, int b) {
        return a ^ b;
    }


}
