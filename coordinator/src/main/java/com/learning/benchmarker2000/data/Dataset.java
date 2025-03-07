package com.learning.benchmarker2000.data;

import java.util.List;

public record Dataset (
        String datasetName,
        Type type,
        List<String> unsortedDataset
) {
    enum Type {INTEGER, STRING}
}
