package com.adventofcode.flashk.day05;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Update {

    private List<Integer> pages;

    @Getter
    private int middlePage;

    public Update(String input) {
        pages = Arrays.stream(input.split(",")).map(Integer::valueOf).toList();
        middlePage = pages.get(pages.size()/2);
    }

    public boolean isOrderedBy(Map<Integer, Set<Integer>> orderingRules) {

        boolean isOrdered = true;

        int i = 0;
        while(isOrdered && i < pages.size()) {
            int page = pages.get(i);
            isOrdered = isOrdered(i, orderingRules.getOrDefault(page, Collections.emptySet()));
            i++;
        }

        return isOrdered;
    }

    public int order(PageComparator comparator) {

        pages = pages.stream().sorted(comparator).toList();
        middlePage = pages.get(pages.size()/2);

        return middlePage;
    }

    private boolean isOrdered(int pageIndex, Set<Integer> orderingRules) {

        if(orderingRules.isEmpty()) {
            return true;
        }

        boolean isOrdered = true;
        int i = pageIndex-1;

        // Look back
        while(isOrdered && i >= 0) {
            int previousPage = pages.get(i);
            isOrdered = !orderingRules.contains(previousPage);
            i--;
        }

        return isOrdered;
    }
}
