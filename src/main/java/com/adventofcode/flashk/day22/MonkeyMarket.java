package com.adventofcode.flashk.day22;

import java.util.List;

public class MonkeyMarket {

    private List<Long> initialSecretNumbers;

    public MonkeyMarket(List<Integer> inputs) {
        initialSecretNumbers = inputs.stream().map(v -> (long) v).toList();
    }

    public long solveA(int numbersToGenerate) {
        long result = 0;

        for(long secretNumber : initialSecretNumbers) {
            result += calculateSecretNumber(secretNumber,numbersToGenerate);
        }

        return result;
    }

    private long calculateSecretNumber(long secretNumber, int numbersToGenerate) {
        long modifiedSecretNumber = secretNumber;
        for(long i = 0; i < numbersToGenerate; i++) {
            modifiedSecretNumber = calculateSecretNumber(modifiedSecretNumber);
        }
        return modifiedSecretNumber;
    }

    private long calculateSecretNumber(long secretNumber) {

        long modifiedSecretNumber = secretNumber;

        // Step 1
        long mulNumber = modifiedSecretNumber * 64;
        modifiedSecretNumber ^= mulNumber;
        modifiedSecretNumber %= 16777216;

        // Step 2
        long divNumber = Math.floorDiv(modifiedSecretNumber, 32);
        modifiedSecretNumber ^= divNumber;
        modifiedSecretNumber %= 16777216;

        // Step 3
        mulNumber = modifiedSecretNumber * 2048;
        modifiedSecretNumber ^= mulNumber;
        modifiedSecretNumber %= 16777216;

        return modifiedSecretNumber;
    }


}
