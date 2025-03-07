package com.learning.benchmarker2000.results;

import java.util.List;

public record Result(
        String solverName,
        String algorithmName,
        String datasetName,
        List<Outcome> outcomes
) {
    public record Outcome(
            Long size,
            Long usedMemory,
            Long elapsedTime
    ) {
    }
}
