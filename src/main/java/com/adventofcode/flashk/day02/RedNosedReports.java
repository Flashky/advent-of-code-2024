package com.adventofcode.flashk.day02;

import java.util.List;

public class RedNosedReports {

    private List<Report> reports;

    public RedNosedReports(List<String> inputs) {
        reports = inputs.stream().map(Report::new).toList();
    }

    public int solveA() {
        int result = 0;
        for(Report report : reports) {
            if(report.isSafe()) {
                result++;
            }
        }

        return result;
    }

    public int solveB() {
        int result = 0;
        for(Report report : reports) {
            if(report.isSafeDampener()) {
                result++;
            }
        }

        return result;
    }
}
