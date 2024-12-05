package com.adventofcode.flashk.day05;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrintQueue {

    private final Map<Integer, Set<Integer>> orderingRules = new HashMap<>();
    private final List<Update> updates = new ArrayList<>();

    public PrintQueue(List<String> inputs) {
        boolean isUpdate = false;
        for(String input : inputs) {
            if(StringUtils.isBlank(input)) {
                isUpdate = true;
                continue;
            }

            if(!isUpdate) {
                String[] keyValue = input.split("\\|");

                Integer key = Integer.valueOf(keyValue[0]);
                Integer value = Integer.valueOf(keyValue[1]);

                if(orderingRules.containsKey(key)){
                    orderingRules.get(key).add(value);
                } else {
                    Set<Integer> values = new HashSet<>();
                    values.add(value);
                    orderingRules.put(key, values);
                }
            } else {
                updates.add(new Update(input));
            }
        }
    }


    public long solveA() {
        return updates.stream()
                .filter(update -> update.isOrderedBy(orderingRules))
                .mapToInt(Update::getMiddlePage)
                .sum();
    }

    public long solveB() {
        PageComparator comparator = new PageComparator(orderingRules);
        return updates.stream()
                .filter(update -> !update.isOrderedBy(orderingRules))
                .mapToInt(update -> update.order(comparator))
                .sum();
    }
}
