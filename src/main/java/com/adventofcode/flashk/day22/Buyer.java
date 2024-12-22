package com.adventofcode.flashk.day22;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Buyer {

    private final List<Long> secretNumbers = new ArrayList<>();

    @Getter
    private final List<Integer> prices = new ArrayList<>();

    @Getter
    private final List<Integer> priceVariations = new ArrayList<>();

    public Buyer(long initialSecretNumber, int numbersToGenerate) {
        calculateSecretNumbers(initialSecretNumber, numbersToGenerate);

        // Prices calculation
        prices.add((int)initialSecretNumber % 10);
        prices.addAll(secretNumbers.stream().map(value -> value.intValue() % 10).toList());

        // Prices variations
        calculatePriceVariations();

    }

    private void calculatePriceVariations() {
        for(int i = 1; i < prices.size(); i++) {
            priceVariations.add(prices.get(i)-prices.get(i-1));
        }
    }

    private void calculateSecretNumbers(long secretNumber, int numbersToGenerate) {
        long modifiedSecretNumber = secretNumber;
        for(long i = 0; i < numbersToGenerate; i++) {
            modifiedSecretNumber = calculateSecretNumber(modifiedSecretNumber);
            secretNumbers.add(modifiedSecretNumber);
        }
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

    public long getLastSecretNumber() {
        return secretNumbers.getLast();
    }

    public Set<PriceSequence> getPriceSequences() {

        Set<PriceSequence> pricesequences = new HashSet<>();

        for(int i = 0; i < prices.size(); i++) {
            int j = i+4;
            //if(j < prices.size() && prices.get(i).equals(prices.get(j))) {
            if(j < prices.size()) {
                PriceSequence sequence = getSequence(i);
                pricesequences.add(sequence);
            }
        }

        return pricesequences;
    }

    /// Attempts to sell to the buyer using a price sequence.
    /// @param priceSequence the price variation sequence to find.
    /// @return a price between `0` and `9` that indicates the price of the first price sequence match.
    /// It will return `0` if there are no matches.
    public int sell(PriceSequence priceSequence) {

        int firstNumber = priceSequence.first();
        int secondNumber = priceSequence.second();
        int thirdNumber = priceSequence.third();
        int fourthNumber = priceSequence.fourth();

        for(int i = 0; i < priceVariations.size(); i++){

            int j = i+3;

            if(j >= priceVariations.size()) {
                return 0;
            }

            boolean match = priceVariations.get(i).equals(firstNumber);
            match = match && priceVariations.get(i+1).equals(secondNumber);
            match = match && priceVariations.get(i+2).equals(thirdNumber);
            match = match && priceVariations.get(i+3).equals(fourthNumber);

            if(match) {
                return prices.get(i+4);
            }
        }

        return 0;
    }

    private PriceSequence getSequence(int index) {
        List<Integer> sequence =  priceVariations.stream().skip(index).limit(4).toList();
        return new PriceSequence(sequence.get(0), sequence.get(1), sequence.get(2), sequence.get(3));
    }
}
