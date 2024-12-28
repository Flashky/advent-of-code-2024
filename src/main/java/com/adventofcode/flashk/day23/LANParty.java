package com.adventofcode.flashk.day23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LANParty {

    private final
    Map<String, Set<String>> graph = new HashMap<>();
    private final Set<String> computerCandidates = new HashSet<>();

    public LANParty(List<String> inputs) {
        for(String input : inputs) {
            String[] data = input.split("-");
            String pc1 = data[0];
            String pc2 = data[1];

            Set<String> connectedPcs = graph.getOrDefault(pc1, new HashSet<>());
            connectedPcs.add(pc2);
            graph.put(pc1, connectedPcs);

            if(pc1.startsWith("t")) {
                computerCandidates.add(pc1);
            }

            connectedPcs = graph.getOrDefault(pc2, new HashSet<>());
            connectedPcs.add(pc1);
            graph.put(pc2, connectedPcs);

            if(pc2.startsWith("t")) {
                computerCandidates.add(pc2);
            }
        }

    }

    public long solveA() {

        Set<Set<String>> networks = new HashSet<>();

        for(String computer : computerCandidates) {
            findNetworks(0,computer,computer, new ArrayList<>(), networks);
        }

        return networks.size();
    }

    private void findNetworks(int pathSize, final String initialComputer, String computer, List<String> path, Set<Set<String>> networks) {

        if((pathSize == 3) && (initialComputer.equals(computer))) {
            networks.add(new HashSet<>(path));
            return;
        } else if(pathSize == 3) {
            return;
        } else if((pathSize > 1) && (initialComputer.equals(computer))) {
            return;
        }

        Set<String> childComputers = graph.get(computer);
        for(String childComputer : childComputers) {
            path.add(childComputer);
            findNetworks(pathSize+1, initialComputer, childComputer, path, networks);
            path.remove(childComputer);
        }

    }

}
