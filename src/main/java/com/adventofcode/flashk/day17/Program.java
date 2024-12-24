package com.adventofcode.flashk.day17;

import java.util.List;
import java.util.StringJoiner;

public class Program {

    public long a;
    public long b;
    public long c;

    public Program(List<String> inputs) {
        a = Integer.parseInt(inputs.get(0).substring(12));
        b = Integer.parseInt(inputs.get(1).substring(12));
        c = Integer.parseInt(inputs.get(2).substring(12));
    }

    // Importante: este programa solo representa el programa de input.data
    // Cualquier otro programa tendría un set de instrucciones diferente.
    public String execute() {
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

        System.out.println(joiner.toString());
        return joiner.toString();
    }
}
