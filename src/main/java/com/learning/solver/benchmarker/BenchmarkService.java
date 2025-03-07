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
                BenchmarkRunner.benchmark(new ArrayList<>(dataset.unsortedDataset()), Solver::quickSort, "quickSort-sort", dataset.datasetName()),
                BenchmarkRunner.benchmark(new ArrayList<>(dataset.unsortedDataset()), Solver::mergeSort, "mergeSort-sort", dataset.datasetName()),
                BenchmarkRunner.benchmark(new ArrayList<>(dataset.unsortedDataset()), Solver::insertionSort, "insertion-sort", dataset.datasetName()),
                BenchmarkRunner.benchmark(new ArrayList<>(dataset.unsortedDataset()), Solver::heapSort, "heapSort-sort", dataset.datasetName())
        );

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        results.forEach(result -> {
            var request = new HttpEntity<>(result, headers);
            var r = restTemplate.exchange(solverAddress + "/result", HttpMethod.POST, request, String.class);

        });
    }
}
