package com.adventofcode.flashk.day23;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.ClusteringCoefficient;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LANParty2 {

    private final Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    public LANParty2(List<String> inputs) {
        for(String input : inputs) {

            String[] data = input.split("-");
            String pc1 = data[0];
            String pc2 = data[1];

            graph.addVertex(pc1);
            graph.addVertex(pc2);
            graph.addEdge(pc1,pc2);

        }

    }

    public String solveB() {

        // Obtain best clustering scores
        ClusteringCoefficient<String, DefaultEdge> clustering = new ClusteringCoefficient<>(graph);
        Map<String,Double> scores = clustering.getScores();

        Optional<Double> maxScore = scores.values().stream().max(Double::compareTo);

        if(maxScore.isEmpty()) {
            return StringUtils.EMPTY;
        }

        // Retrieve the vertexes candidates that have the higher score
        Set<String> candidates = scores.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(maxScore.get()))
                .map(Map.Entry::getKey).collect(Collectors.toSet());

        // In case of multiple clusters having the same score, generate a second best score:
        // This best score is based in obtaining the ratio: (number of neighbours with best score / neighbours)
        // Anytime a best score is found, save the candidate vertexes into a set.
        float bestScore = 0;
        Set<String> bestCluster = new HashSet<>();
        for(String candidate : candidates) {
            Set<String> neighbours = Graphs.neighborSetOf(graph, candidate);
            Set<String> commonVertexes = SetUtils.intersection(candidates, neighbours);
            float score = (float) commonVertexes.size() / (float) neighbours.size();
            if(score > bestScore) {
                bestCluster = new HashSet<>(commonVertexes);
                bestCluster.add(candidate);
                bestScore = score;
            }
        }
        return bestCluster.stream().sorted().collect(Collectors.joining(","));
    }

}
