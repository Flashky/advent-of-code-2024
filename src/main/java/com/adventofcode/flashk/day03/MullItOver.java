package com.adventofcode.flashk.day03;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        String complete = String.join("", inputs);
        String cleanedInput = removeMultiplications(complete);
        Matcher matcher = PATTERN.matcher(cleanedInput);
        while(matcher.find()){
            result += multiply(matcher.group());
        }


        return result;
    }

    private String removeMultiplications(String input) {

        String modifiedInput = IGNORE_MULTIPLICATIONS.matcher(input).replaceAll("");

        if(!modifiedInput.contains("don't()")) {
            return modifiedInput;
        }

        return modifiedInput.substring(0, modifiedInput.indexOf("don't()"));

    }

    private long multiply(String operation) {
        Matcher matcher = OPERATORS_PATTERN.matcher(operation);

        long op1;
        long op2;
        if(matcher.find()){
            op1 = Long.parseLong(matcher.group(1));
            op2 = Long.parseLong(matcher.group(2));
            return op1 * op2;
        }
        return 0;
    }
}
