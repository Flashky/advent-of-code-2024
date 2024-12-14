package com.adventofcode.flashk.day14;

import com.adventofcode.flashk.common.Vector2;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Robot {


    private static final Pattern ROBOT_PATTERN = Pattern.compile("p=(-?\\d*),(-?\\d*) v=(-?\\d*),(-?\\d*)");

    @Getter
    private Vector2 position;
    private final Vector2 direction;

    public Robot(String input) {
        super();

        Matcher matcher = ROBOT_PATTERN.matcher(input);

        if(matcher.find()) {

            String px = matcher.group(1);
            String py = matcher.group(2);
            String vx = matcher.group(3);
            String vy = matcher.group(4);

            position = new Vector2(Integer.parseInt(px), Integer.parseInt(py));
            direction = new Vector2(Integer.parseInt(vx), Integer.parseInt(vy));
        } else {
            throw new IllegalArgumentException("Invalid string");
        }

    }

    public void move(int seconds, int rows, int cols) {
        for(int i = 0; i < seconds; i++) {
            move(rows, cols);
        }
    }

    public void move(int rows, int cols) {
        int x1 = position.getX() + direction.getX();
        int y1 = position.getY() + direction.getY();
        int x2 = (cols + x1) % cols;
        int y2 = (rows + y1) % rows;
        position = new Vector2(x2,y2);
    }


}
