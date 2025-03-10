package com.learning.solver.benchmarker;

import com.learning.solver.domain.Result;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@UtilityClass
public class BenchmarkRunner {

    public static Result benchmark(List<String> data, Function<List<String>, List<String>> function, String algorithm, String dataset) {

        var allResultData = new ArrayList<Result.Outcome>();

        var resultData = singleRun(data, function);
        allResultData.add(resultData);

        return new Result(algorithm, dataset, List.copyOf(allResultData));
    }

    private static Result.Outcome singleRun(List<String> data, Function<List<String>, List<String>> function) {
        Runtime rt = Runtime.getRuntime();
        long totalMemory = rt.totalMemory();
        long startTime = System.currentTimeMillis();

        List<String> result = function.apply(data);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        long freeMemory = rt.freeMemory();
        long usedMemory = totalMemory - freeMemory;


        return new Result.Outcome(
                (long) data.size(), usedMemory, elapsedTime
        );
    }
}
