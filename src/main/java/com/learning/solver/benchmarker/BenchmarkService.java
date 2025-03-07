package com.learning.solver.benchmarker;

import com.learning.solver.controller.Dataset;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BenchmarkService {
    private final RestTemplate restTemplate;
    private static final String solverAddress = "http://localhost:8080";

    public BenchmarkService() {
        this.restTemplate = new RestTemplate();
    }

    public void benchmark(Dataset dataset) {

        var results = List.of(
                BenchmarkRunner.benchmark(new ArrayList<>(dataset.unsortedDataset()), Solver::quickSort, "quickSort-sort-java", dataset.datasetName()),
                BenchmarkRunner.benchmark(new ArrayList<>(dataset.unsortedDataset()), Solver::insertionSort, "insertion-sort-java", dataset.datasetName()),
                BenchmarkRunner.benchmark(new ArrayList<>(dataset.unsortedDataset()), Solver::librarySort, "library-sort-java", dataset.datasetName())
        );
        System.out.println(results);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        results.forEach(result -> {
            var request = new HttpEntity<>(result, headers);
            restTemplate.exchange(solverAddress + "/result", HttpMethod.POST, request, String.class);
        });
    }
}
