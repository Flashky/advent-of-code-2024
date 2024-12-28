package com.adventofcode.flashk.day21.redesign;


import lombok.Getter;

public enum Key {

    NUMBER_0("0"),
    NUMBER_1("1"),
    NUMBER_2("2"),
    NUMBER_3("3"),
    NUMBER_4("4"),
    NUMBER_5("5"),
    NUMBER_6("6"),
    NUMBER_7("7"),
    NUMBER_8("8"),
    NUMBER_9("9"),
    ARROW_LEFT("<"),
    ARROW_DOWN("v"),
    ARROW_UP("^"),
    ARROW_RIGHT(">"),
    ACCEPT("A");

    @Getter
    private final String value;

    Key(String value) {
        this.value = value;
    }

    /*
    public static Key getKey(String value) {
        for(Key key : values()) {
            if(key.getValue().equals(value)) {
                return key;
            }
        }
        throw new IllegalArgumentException("Unknown enum value for: "+value);
     }*/

}
