package com.adventofcode.flashk.day22;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonkeyMarket {

    public static final int NUMBERS_TO_GENERATE = 2000;

    private final List<Buyer> buyers;

    public MonkeyMarket(List<Integer> inputs, int numbersToGenerate) {
        buyers = inputs.stream().map(v -> new Buyer(v, numbersToGenerate)).toList();
    }

    public MonkeyMarket(List<Integer> inputs) {
        buyers = inputs.stream().map(v -> new Buyer(v, NUMBERS_TO_GENERATE)).toList();
    }

    public long solveA() {
        return buyers.stream().mapToLong(Buyer::getLastSecretNumber).sum();
    }

    public long solveB() {
        // 802 price sequences en list
        // 688 price sequences en set. Comprobado en excel, esto es ok.
        Set<PriceSequence> priceSequences = new HashSet<>();
        for(Buyer buyer : buyers) {
            priceSequences.addAll(buyer.getPriceSequences());
        }

        long bestResult = Long.MIN_VALUE;

        // Cross all price sequence against all buyers
        for(PriceSequence priceSequence: priceSequences) {
            long currentResult = 0;
            for(Buyer buyer : buyers) {
                currentResult += buyer.sell(priceSequence);
            }

            bestResult = Math.max(bestResult, currentResult);
        }

        return bestResult;
    }



}
