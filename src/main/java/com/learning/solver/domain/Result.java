package com.learning.solver.domain;

import java.util.List;

public record Result(
        String solverName,
        String algorithmName,
        String datasetName,
        List<Outcome> outcomes
) {

    public Result(String algorithmName,
                  String datasetName,
                  List<Outcome> outcomes
    ) {
        this("java", algorithmName, datasetName, outcomes);
    }

    public record Outcome(
            Long size,
            Long usedMemory,
            Long elapsedTime
    ) {

    }
}
