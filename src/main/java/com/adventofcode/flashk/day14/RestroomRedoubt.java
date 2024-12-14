package com.adventofcode.flashk.day14;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

import java.util.List;
import java.util.Optional;

public class RestroomRedoubt {

    private final int rows;
    private final int cols;

    private final List<Robot> robots;

    public RestroomRedoubt(List<String> inputs, int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        robots = inputs.stream().map(Robot::new).toList();
    }

    public int solveA(int seconds) {

        int q1Robots = 0;
        int q2Robots = 0;
        int q3Robots = 0;
        int q4Robots = 0;

        Collider2D quadrant1 = new Collider2D(new Vector2(0,0), new Vector2((cols/2)-1, (rows/2)-1));
        Collider2D quadrant2 = new Collider2D(new Vector2((cols/2)+1,0), new Vector2(cols-1, (rows/2)-1));
        Collider2D quadrant3 = new Collider2D(new Vector2(0,(rows/2)+1), new Vector2((cols/2)-1,rows-1));
        Collider2D quadrant4 = new Collider2D(new Vector2((cols/2)+1,(rows/2)+1), new Vector2(cols-1,rows-1));

        for(Robot robot : robots) {
            robot.move(seconds, rows, cols);
            // quadrant1.collidesWith(robot.getPosition()) was not working. Possible reasons:
            // - collidesWith(Vector2) is only checking if the point is part of a line instead of an area.
            // - Didn't test diagonal test cases
            if(quadrant1.collidesWith(new Collider2D(robot.getPosition()))) {
                q1Robots++;
            } else if(quadrant2.collidesWith(new Collider2D(robot.getPosition()))) {
                q2Robots++;
            } else if(quadrant3.collidesWith(new Collider2D(robot.getPosition()))) {
                q3Robots++;
            } else if(quadrant4.collidesWith(new Collider2D(robot.getPosition()))) {
                q4Robots++;
            }
        }

        return q1Robots * q2Robots * q3Robots * q4Robots;
    }

    public int solveB() {
        int seconds = 0;
        long count = -1;

        do {
            seconds++;
            for (Robot robot : robots) {
                robot.move(rows, cols);
                count = robots.stream().map(Robot::getPosition).distinct().count();
            }
        } while (count != robots.size()) ;

        paint();

        return seconds;

    }

    private void paint() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                final int x = col;
                final int y = row;
                Optional<Robot> robot = robots.stream()
                        .filter(r -> r.getPosition().getX() == x)
                        .filter(r -> r.getPosition().getY() == y)
                        .distinct()
                        .findFirst();


                if(robot.isPresent()) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
