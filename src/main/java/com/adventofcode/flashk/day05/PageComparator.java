package com.adventofcode.flashk.day05;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class PageComparator implements Comparator<Integer> {

    private final Map<Integer, Set<Integer>> orderingRules;

    public PageComparator(Map<Integer, Set<Integer>> orderingRules) {
        this.orderingRules = orderingRules;
    }

    @Override
    public int compare(Integer o1, Integer o2) {

        // -1 si o1 precede a o2
        // 1 si o2 precede a o1
        // Resto de casos: 0

        Set<Integer> afterPagesO1 = orderingRules.getOrDefault(o1, Collections.emptySet());

        if(afterPagesO1.contains(o2)) {
            return -1;
        }

        Set<Integer> afterPagesO2 = orderingRules.getOrDefault(o2, Collections.emptySet());

        if(afterPagesO2.contains(o1)) {
            return 1;
        }

        return 0;
    }
}
