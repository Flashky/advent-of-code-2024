package com.adventofcode.flashk.day02;

import java.util.List;

public class RedNosedReports {

    private List<Report> reports;

    public RedNosedReports(List<String> inputs) {
        reports = inputs.stream().map(Report::new).toList();
    }

    public long solveA() {
        return reports.stream().filter(Report::isSafe).count();
    }

    public long solveB() {
        return reports.stream().filter(Report::isSafeDampener).count();
    }
}
