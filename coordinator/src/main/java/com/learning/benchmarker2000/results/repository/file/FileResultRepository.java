package com.learning.benchmarker2000.results.repository.file;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.benchmarker2000.results.Result;
import com.learning.benchmarker2000.results.repository.ResultRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.StreamSupport;


@Repository
class FileResultRepository implements ResultRepository {
    
    private static final String BASE_PATH = "src/main/java/com/learning/benchmarker2000/results/repository/file/storage/";

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void save(Result result) {
        getPaths(result).forEach((metricType, trace) -> writeResult(result, trace));
    }

    private void writeResult(Result result, Trace trace) {
        try {
            var path = Paths.get(trace.getFullPath());
            Files.createDirectories(Paths.get(trace.getPathTillDataSet()));
            var combinedOutcomes = new ArrayList<>(result.outcomes());

            if (Files.exists(path)) {
                var json = objectMapper.readTree(path.toFile());
                var existingOutcomes = getExistingOutcomes(json);
                combinedOutcomes.addAll(existingOutcomes);
            }
            
            writeToFile(path, objectMapper, combinedOutcomes);
        } catch (IOException ignored) {
        }
    }

    private static void writeToFile(Path path, ObjectMapper objectMapper, ArrayList<Result.Outcome> combinedOutcomes) throws IOException {
        Files.write(
                path,
                objectMapper.writeValueAsString(combinedOutcomes).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
        );
    }

    private static List<Result.Outcome> getExistingOutcomes(JsonNode json) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(json.elements(), 0), false)
                .map(node -> new Result.Outcome(
                        node.get("size").asLong(),
                        node.get("usedMemory").asLong(),
                        node.get("elapsedTime").asLong()
                ))
                .toList();
    }

    private static Map<MetricType, Trace> getPaths(Result result) {
        return Map.of(
                MetricType.MEMORY, new Trace(BASE_PATH + "memory/", result.datasetName(), result.algorithmName()),
                MetricType.TIME, new Trace(BASE_PATH + "time/", result.datasetName(), result.algorithmName())
        );
    }
}

record Trace(String basePath, String datasetName, String filename) {

    String getFullPath() {
        return getPathTillDataSet() + "/" + filename + ".json";
    }

    String getPathTillDataSet() {
        return basePath + "/" + datasetName;
    }
}
