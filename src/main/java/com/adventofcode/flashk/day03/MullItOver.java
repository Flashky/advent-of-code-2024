package com.adventofcode.flashk.day03;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {

    // [0-9][0-9]?[0-9]?,[0-9][0-9]?[0-9]?
    private static final Pattern PATTERN = Pattern.compile("(mul\\([0-9][0-9]?[0-9]?,[0-9][0-9]?[0-9]?\\))");
    private static final Pattern OPERATORS_PATTERN = Pattern.compile("([0-9][0-9]?[0-9]?),([0-9][0-9]?[0-9]?)");
    private static final Pattern IGNORE_MULTIPLICATIONS = Pattern.compile("don't\\(\\).*?do\\(\\)");

    private List<String> inputs;

    public MullItOver(List<String> inputs) {
        this.inputs = inputs;
    }

    public long solveA() {

        long result = 0;
        for(String input : inputs) {
            Matcher matcher = PATTERN.matcher(input);

            while(matcher.find()){
                result += multiply(matcher.group());
            }
        }

        return result;
    }

    public long solveB() {
        long result = 0;
        for(String input : inputs) {

            String cleanedInput = removeMultiplications(input);

            Matcher matcher = PATTERN.matcher(cleanedInput);

            while(matcher.find()){
                result += multiply(matcher.group());
            }
        }

        return result;
    }

    private String removeMultiplications(String input) {

        String modifiedInput = input;

        Matcher matcher = IGNORE_MULTIPLICATIONS.matcher(modifiedInput);
        if(matcher.find()) {
            modifiedInput = modifiedInput.replaceAll("don't\\(\\).*?do\\(\\)", "");
        }

        if(!modifiedInput.contains("don't()")) {
            return modifiedInput;
        }

        return modifiedInput.substring(0, modifiedInput.indexOf("don't()"));

        /*
        while(modifiedInput.contains("don't()")) {
            //int startRemoveIndex = modifiedInput.indexOf("don't()");
           //int endRemoveIndex = modifiedInput.indexOf("do()") + "do()".length();

            // Ojo:
            // Y si tienes...: don't() ... do() ... do() ... don't() ?

            Matcher matcher = IGNORE_MULTIPLICATIONS.matcher(modifiedInput);
            if(matcher.find()) {
                modifiedInput = modifiedInput.replaceAll("don't\\(\\).*?do\\(\\)", "");
            } else {
                modifiedInput = modifiedInput.replaceAll("don't\\(\\)", "");
            }

            // Y si tienes un don't() sin cerrar?
            //modifiedInput = modifiedInput.substring(0, startRemoveIndex) + modifiedInput.substring(endRemoveIndex, modifiedInput.length());
        }

        return modifiedInput;*/
    }

    private long multiply(String operation) {
        Matcher matcher = OPERATORS_PATTERN.matcher(operation);

        long op1;
        long op2;
        if(matcher.find()){
            op1 = Long.valueOf(matcher.group(1));
            op2 = Long.valueOf(matcher.group(2));
            return op1 * op2;
        }
        return 0;
    }
}
