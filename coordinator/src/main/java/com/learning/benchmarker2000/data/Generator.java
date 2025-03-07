package com.learning.benchmarker2000.data;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class Generator {

    private final static String DATASET_NAME_PREFIX = "RandomStrings";

    public Dataset generateDataset(int size) {
        var seed = UUID.randomUUID().getMostSignificantBits();
        List<String> data = new ArrayList<>(size);
        var random = new Random(seed);
        for (int i = 0; i < size; i++) {
            data.add("Item" + random.nextInt(1000));
        }
        return new Dataset(
                DATASET_NAME_PREFIX + "-" + seed,
                Dataset.Type.STRING,
                data
        );
    }
}
