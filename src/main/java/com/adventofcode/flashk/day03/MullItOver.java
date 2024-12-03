package com.adventofcode.flashk.day03;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOver {

    private static final Pattern MULTIPLY_PATTERN = Pattern.compile("(mul\\([0-9][0-9]?[0-9]?,[0-9][0-9]?[0-9]?\\))");
    private static final Pattern OPERATORS_PATTERN = Pattern.compile("([0-9][0-9]?[0-9]?),([0-9][0-9]?[0-9]?)");
    private static final Pattern IGNORE_MULTIPLICATIONS = Pattern.compile("don't\\(\\).*?do\\(\\)");
    private static final String DONT = "don't()";

    private final List<String> inputs;

    public MullItOver(List<String> inputs) {
        this.inputs = inputs;
    }

    public long solve(boolean cleanMemory) {

        // The input is just a single word, even when the input is a list of lines.
        String input = String.join(StringUtils.EMPTY, inputs);
        String memory = cleanMemory ? clean(input) : input;

        long result = 0;
        Matcher matcher = MULTIPLY_PATTERN.matcher(memory);

        while(matcher.find()){
            result += multiply(matcher.group());
        }

        return result;
    }

    private String clean(String input) {
        String modifiedInput = IGNORE_MULTIPLICATIONS.matcher(input).replaceAll(StringUtils.EMPTY);
        return modifiedInput.contains(DONT) ? modifiedInput.substring(0, modifiedInput.indexOf(DONT)) : modifiedInput;
    }

    private long multiply(String operation) {
        Matcher matcher = OPERATORS_PATTERN.matcher(operation);
        return matcher.find() ? Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2)) : 0;
    }
}
