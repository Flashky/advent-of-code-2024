package com.adventofcode.flashk.day17;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        a = Integer.parseInt(inputs.get(0).substring(12));
        b = Integer.parseInt(inputs.get(1).substring(12));
        c = Integer.parseInt(inputs.get(2).substring(12));

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

        // TODO Posible problema de memoization
        // Brainstorming
        // programStates.stream().sorted().toList()
        //
        // En el sample:
        // Al generar un conjunto de estados, se observa algo interesante
        // - Cada 8 valores diferenes de a, se repite el mismo out.
        // - Por lo tanto, podemos reducir el espacio de búsqueda en el sample para i+8.

        // https://www.reddit.com/r/adventofcode/s/6H6hRO2M9r

        long originalB = this.b;
        long originalC = this.c;
        //
        // Idea: 2^48
        // La primera solución de programa que tiene exactamente 16 elementos empieza en
        // el índice 2^48:

        // 2^3  =    8 -> 1 elemento
        // 2^6  =   64 -> 3 elementos
        // 2^9  =  512 -> 4 elementos
        // 2^12 = 1024 -> 5 elementos
        // 16 elementos?
        // 2^48 = 281474976710656
        // 1 elementos -> 0+3=3
        // 3 elementos -> 3+3=6
        // 4 elementos -> 6+3=9
        // 5 elementos -> 9+3=12
        //
        // Fórmula = 3+(3*(n-1))
        // Por lo tanto:
        // 16 elementos -> 3+(3*(16-1)) = 3+(3*15) = 3+45 = 48
        // Potencia: 2^48

        //long initialValue = (long) Math.pow(2,48);
        //long maxValue = (long) Math.pow(2,49);
        // Cúantas iteraciones hay entre 2^48 y 2^49?
        // Es algún número entre 2^48 y 2^49

        // Dígito 48: 6
        //List<Long> values = List.of(0L,1L,2L,3L,4L,5L,6L,7L);

        // Dígito 47: 0
        //List<Long> values = List.of(6L,16L, 26L, 36L, 46L, 56L, 66L, 76L);

        // Dígito 46: 0
        //List<Long> values = List.of(6L,106L, 206L, 306L, 406L, 506L, 606L, 706L);

        String[] binaryDigits = { "000", "001", "010", "011", "100", "101", "110", "111" };

        //debug(46, 846, originalB, originalC);

        int index = 0;
        List<Long> numberValues = new ArrayList<>();
        long realNumber = 0;
        long previousNumber = 0;
        for(int i = 0; i < 16; i++) {
            programStates.clear();
            //realNumber +=  numberDigit * Math.pow(10, index);
            for(int digit = 0; digit < 9; digit++) {
                // Convertir realNumber a binario

                realNumber = previousNumber + (digit * (long) Math.pow(10, index));

                //String binarySection = binaryDigits[digit];
                //String binaryNumber = StringUtils.rightPad(binarySection,3*(i+1),"0");
                //Long number = Long.valueOf(binaryNumber,2);

                debug(digit, realNumber, originalB, originalC);
            }
            index++;

            previousNumber = programStates.stream()
                         .filter(ps -> ps.a() == 0)
                                       .filter(ps -> ps.b() == 0)
                                       .filter(ps -> ps.c() == 0)
                                       .mapToLong(ProgramState::number).findFirst().getAsLong();


        }
        /*
        List<Long> values = List.of(6L,1006L, 2006L, 3006L, 4006L, 5006L, 6006L, 7006L);
        for(Long value : values) {
            debug(value, originalB, originalC);
        }*/
        /*
        for(long i = 0; i < 8; i++) {
            try {
                if(expectedProgram.equals(debug(i, originalB, originalC))) {
                    System.out.println("Found: "+i);
                    return i;
                }
            } catch(IllegalArgumentException e) {
                System.out.println("Invalid operator: "+i);
            }
        }*/
        System.out.println("Not found");
        return -1;
    }

    public String debug(long number, long a, long b, long c) {

        //System.out.println("Testing a:"+a);
        this.a = a;
        this.b = b;
        this.c = c;

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

            /*
            if(opcode == 5) {
                //System.out.println("Current: " + outJoiner.toString());
                //System.out.println("Expected: "+expectedProgram);
                if(!expectedProgram.startsWith(outJoiner.toString())) {
                    programStates.add(new ProgramState(this.a, this.b, this.c, outJoiner.toString()));
                    return "";
                }
            }*/


        }
        programStates.add(new ProgramState(number, a, this.b, this.c, outJoiner.toString()));
        //System.out.println("Completed: " + outJoiner.toString());
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

    private void bxc(long operand) {
        // https://en.wikipedia.org/wiki/Bitwise_operation#XOR
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
