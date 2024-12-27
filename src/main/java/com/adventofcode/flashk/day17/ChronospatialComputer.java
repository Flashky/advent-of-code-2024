package com.adventofcode.flashk.day17;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
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

    private List<ProgramState> programStates = new ArrayList<>();

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

        // TODO
        // https://www.reddit.com/r/adventofcode/comments/1hn01ke/2024_day_17_part_2_code_works_until_certain/

        // Aparentemente no basta con quedarse con el primer octal que verifique la condición.
        // Puede haber más de un octal válido.
        // Posible algoritmo recursivo con backtracking.
        // Podría hacer el algoritmo como una mezcla recursiva e iterativa:
        // - Recursivamente vamos bajando por dígitos (0 al 15)
        // - Iterativamente, en cada llamda recursiva:
        //   - Generamos en un bucle los 8 posibles valores octales, llamamos a solveA para ver si es solución parcial.
        //   - Si es solución parcial, hacemos llamada recursiva, si no, iteramos al siguiente valor.
        // Casos base:
        // - Happy Path: dígito 16, y para el dígito octal elegido,la cadena generada es exactamente igual que la esperada

        // Parámetros recursivos:
        // - octal string: currentOctalDigit + octalDigit


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

    private long findRegistryA(int digit, String currentOctalNumber, String output) {

        // CB
        if(digit == program.length) {
            long result = Long.parseLong(currentOctalNumber, 8);
            return expectedProgram.equals(output) ? result : -1;
            // TODO probablemente pueda devolver directamente el número octal en decimal
        }

        // Caso recursivo
        Map<Long,String> octalDigits = getOctalDigitCandidates(currentOctalNumber);

        for(Map.Entry<Long,String> octalDigit : octalDigits.entrySet()) {
            long result = findRegistryA(digit+1, currentOctalNumber+octalDigit.getKey(), octalDigit.getValue());

            if(result != -1) {
                return result;
            }
        }

        return -1;
    }

    private Map<Long,String> getOctalDigitCandidates(String currentOctalNumber) {

        Map<Long,String> validOctalDigits = new HashMap<>();

        for(long octalDigit = 0; octalDigit < 8; octalDigit++) {

            this.a = Long.parseLong(currentOctalNumber + octalDigit, 8);
            this.b = 0;
            this.c = 0;

            String result = solveA();

            if(expectedProgram.endsWith(result)) {
                validOctalDigits.put(octalDigit, result);
            }
        }

        return validOctalDigits;
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
                throw new IllegalArgumentException("Invalid operator: "+operator);
        }
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
