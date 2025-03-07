package com.learning.benchmarker2000.results.repository.models;

public record SourceId(String solverId, String algorithmId) {
    
    String getName() {
        return solverId + algorithmId;
    }
}
